package org.neogroup.warp.data;

import org.neogroup.warp.Warp;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DataSources {

    private static final String DEFAULT_DATA_SOURCE_NAME_PROPERTY_NAME = "default_data_source_name";

    private final Map<Class, DataSource> dataSources;
    private final Map<String, DataSource> dataSourcesByName;
    private final Map<Long, Map<String, DataConnection>> connections;

    public DataSources() {
        dataSources = new HashMap<>();
        dataSourcesByName = new HashMap<>();
        connections = Collections.synchronizedMap(new HashMap<>());
    }

    public void registerDataSource(Class<? extends DataSource> dataSourceClass) {

        try {
            DataSource dataConnection = dataSourceClass.getConstructor().newInstance();
            dataSources.put(dataSourceClass, dataConnection);
            DataSourceComponent dataSourceComponent = dataSourceClass.getAnnotation(DataSourceComponent.class);
            if (dataSourceComponent != null) {
                dataSourcesByName.put(dataSourceComponent.name(), dataConnection);
            }
        }
        catch (Exception ex) {
            throw new DataException ("Error registering data manager \"" + dataSourceClass.getName() + "\" !!", ex);
        }
    }

    public DataConnection getConnection() {

        String dataSourceName = null;
        if (dataSources.isEmpty()) {
            throw new DataException ("No data connections found !!");
        }

        if (Warp.hasProperty(DEFAULT_DATA_SOURCE_NAME_PROPERTY_NAME)) {
            dataSourceName = Warp.getProperty(DEFAULT_DATA_SOURCE_NAME_PROPERTY_NAME);
        }
        else {
            dataSourceName = dataSourcesByName.keySet().iterator().next();
        }

        return getConnection(dataSourceName);
    }

    public DataConnection getConnection(String dataSourceName) {

        long threadId = Thread.currentThread().getId();
        Map<String, DataConnection> threadConnections = connections.get(threadId);
        if (threadConnections == null) {
            threadConnections = new HashMap<>();
            connections.put(threadId, threadConnections);
        }

        DataConnection connection = threadConnections.get(dataSourceName);
        if (connection == null || connection.isClosed()) {
            DataSource source = dataSourcesByName.get(dataSourceName);
            if (source == null) {
                throw new DataException("No data source with name \"" + dataSourceName + "\"");
            }
            connection = new DataConnection(source.requestConnection());
            threadConnections.put(dataSourceName, connection);
        }

        return connection;
    }

    public void releaseConnections () {

        long threadId = Thread.currentThread().getId();
        Map<String, DataConnection> threadConnections = connections.get(threadId);
        if (threadConnections != null) {
            for (DataConnection connection : threadConnections.values()) {
                connection.close();
            }
            threadConnections.clear();
            connections.remove(threadId);
        }
    }
}
