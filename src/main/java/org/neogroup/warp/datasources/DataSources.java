package org.neogroup.warp.datasources;

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
        if (dataSources.isEmpty()) {
            throw new RuntimeException("No data sourcess where registered !!");
        }
        else if (dataSources.size() == 1) {
            dataSource = dataSources.values().iterator().next();
        }
        else if (Warp.hasProperty(DATA_SOURCES_DEFAULT_DATA_SOURCE_PROPERTY_NAME)) {
            dataSource = getDataSource((String)Warp.getProperty(DATA_SOURCES_DEFAULT_DATA_SOURCE_PROPERTY_NAME));
        }
        else {
            throw new RuntimeException ("Default data source property \"" + DATA_SOURCES_DEFAULT_DATA_SOURCE_PROPERTY_NAME + "\" not set !!");
        }
        return dataSource;
    }
}
