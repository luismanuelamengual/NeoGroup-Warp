package org.neogroup.warp.data;

import org.neogroup.warp.Warp;

import java.util.HashMap;
import java.util.Map;

public class DataSources {

    private static final String DEFAULT_DATA_SOURCE_NAME_PROPERTY_NAME = "default_data_source_name";

    private final Map<Class, DataSource> dataSources;
    private final Map<String, DataSource> dataSourcesByName;

    public DataSources() {
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

        DataSource source = null;
        if (!dataSources.isEmpty()) {

            if (Warp.hasProperty(DEFAULT_DATA_SOURCE_NAME_PROPERTY_NAME)) {
                source = dataSourcesByName.get(Warp.getProperty(DEFAULT_DATA_SOURCE_NAME_PROPERTY_NAME));
            }
            else {
                source = dataSources.values().iterator().next();
            }
        }

        return source.getConnection();
    }

    public DataConnection getConnection(String dataSourceName) {

        return dataSourcesByName.get(dataSourceName).getConnection();
    }
}
