package org.neogroup.warp.data;

import org.neogroup.warp.Warp;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class DataSources {

    private static final String DATA_SOURCES_DEFAULT_DATA_SOURCE_PROPERTY_NAME = "datasources.default.datasource.name";

    private final Map<Class, DataSource> dataSources;
    private final Map<String, DataSource> dataSourcesByName;

    public DataSources() {
        dataSources = new HashMap<>();
        dataSourcesByName = new HashMap<>();
    }

    public void registerDataSource(Class<? extends DataSource> dataSourceClass) {

        try {
            DataSource dataSource = dataSourceClass.getConstructor().newInstance();
            dataSources.put(dataSourceClass, dataSource);

            DataSourceComponent dataSourceComponent = (DataSourceComponent) dataSourceClass.getAnnotation(DataSourceComponent.class);
            if (dataSourceComponent != null) {
                dataSourcesByName.put(dataSourceComponent.name(), dataSource);
            }
        }
        catch (Exception ex) {
            throw new RuntimeException ("Error registering data source \"" + dataSourceClass.getName() + "\" !!", ex);
        }
    }

    public DataSource getDataSource(String dataSourceName) {

        return dataSourcesByName.get(dataSourceName);
    }

    public DataSource getDataSource(Class<? extends DataSource> dataSourceClass) {
        return dataSources.get(dataSourceClass);
    }

    public DataSource getDefaultDataSource() {

        DataSource dataSource = null;
        if (!dataSources.isEmpty()) {

            if (Warp.hasProperty(DATA_SOURCES_DEFAULT_DATA_SOURCE_PROPERTY_NAME)) {
                dataSource = dataSourcesByName.get(Warp.getProperty(DATA_SOURCES_DEFAULT_DATA_SOURCE_PROPERTY_NAME));
            }
            else {
                dataSource = dataSources.values().iterator().next();
            }
        }
        return dataSource;
    }

    public DataConnection getDataConnection () {

        DataSource dataSource = getDefaultDataSource();
        if (dataSource == null) {
            throw new RuntimeException("No default data source was registered");
        }
        return getDataConnection(dataSource);
    }

    public DataConnection getDataConnection (String dataSourceName) {

        DataSource dataSource = getDataSource(dataSourceName);
        if (dataSource == null) {
            throw new RuntimeException("Data source with name \"" + dataSourceName + "\" was not registered");
        }
        return getDataConnection(dataSource);
    }

    public DataConnection getDataConnection (DataSource dataSource) {

        try {
            return new DataConnection(dataSource.getConnection());
        }
        catch (Exception exception) {
            throw new RuntimeException("Error retrieving data connection !!", exception);
        }
    }
}
