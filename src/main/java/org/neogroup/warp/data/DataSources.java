package org.neogroup.warp.data;

import org.neogroup.warp.WarpInstance;

import java.util.HashMap;
import java.util.Map;

public class DataSources {

    private static final String DEFAULT_DATA_SOURCE_NAME_PROPERTY_NAME = "default_data_source_name";

    private final WarpInstance warpInstance;
    private final Map<Class, DataSource> dataSources;
    private final Map<String, DataSource> dataSourcesByName;

    public DataSources(WarpInstance warpInstance) {
        this.warpInstance = warpInstance;
        dataSources = new HashMap<>();
        dataSourcesByName = new HashMap<>();
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

        if (warpInstance.hasProperty(DEFAULT_DATA_SOURCE_NAME_PROPERTY_NAME)) {
            dataSourceName = warpInstance.getProperty(DEFAULT_DATA_SOURCE_NAME_PROPERTY_NAME);
        }
        else {
            dataSourceName = dataSourcesByName.keySet().iterator().next();
        }

        return getConnection(dataSourceName);
    }

    public DataConnection getConnection(String dataSourceName) {

        Map<String, DataConnection> connections = warpInstance.getContext().getConnections();
        DataConnection connection = connections.get(dataSourceName);
        if (connection == null || connection.isClosed()) {
            DataSource source = dataSourcesByName.get(dataSourceName);
            if (source == null) {
                throw new DataException("No data source with name \"" + dataSourceName + "\"");
            }
            connection = new DataConnection(source.requestConnection());
            connections.put(dataSourceName, connection);
        }
        return connection;
    }
}
