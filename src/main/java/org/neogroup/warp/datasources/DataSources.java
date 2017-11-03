package org.neogroup.warp.datasources;

import java.util.HashMap;
import java.util.Map;

public class DataSources {

    private final Map<Class, DataSource> dataSources;
    private final Map<String, DataSource> dataSourcesByName;

    public DataSources() {
        dataSources = new HashMap<>();
        dataSourcesByName = new HashMap<>();
    }

    public void registerDataSource(DataSource dataSource) {

        Class dataSourceClass = dataSource.getClass();
        DataSourceComponent dataSourceComponent = (DataSourceComponent)dataSourceClass.getAnnotation(DataSourceComponent.class);
        dataSources.put(dataSourceClass, dataSource);
        if (dataSourceComponent != null) {
            dataSourcesByName.put(dataSourceComponent.name(), dataSource);
        }
    }
}
