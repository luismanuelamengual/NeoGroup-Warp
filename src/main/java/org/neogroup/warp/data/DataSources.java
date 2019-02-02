package org.neogroup.warp.data;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import static org.neogroup.warp.Warp.*;

public abstract class DataSources {

    private static final String DEFAULT_DATA_SOURCE_NAME_PROPERTY = "org.neogroup.warp.defaultDatasourceName";
    private static final String DEFAULT_DATA_SOURCE_NAME = "main";

    private static final Map<Class, DataSource> dataSources;
    private static final Map<String, DataSource> dataSourcesByName;

    static {
        dataSources = new HashMap<>();
        dataSourcesByName = new HashMap<>();
    }

    public static void registerDataSource(Class<? extends DataSource> dataSourceClass) {

        try {
            DataSource dataConnection = dataSourceClass.getConstructor().newInstance();
            dataSources.put(dataSourceClass, dataConnection);
            DataSourceComponent dataSourceComponent = dataSourceClass.getAnnotation(DataSourceComponent.class);
            String dataSourceName = null;
            if (dataSourceComponent != null) {
                dataSourceName = dataSourceComponent.value();
                dataSourcesByName.put(dataSourceName, dataConnection);
            }
            getLogger().info("Data source \"" + dataSourceClass.getName() + "\" registered !!" + (dataSourceName != null?" [name=" + dataSourceName + "]":""));
        }
        catch (Exception ex) {
            throw new RuntimeException ("Error registering data manager \"" + dataSourceClass.getName() + "\" !!", ex);
        }
    }

    private static String getDefaultSourceName () {
        String dataSourceName = null;
        if (dataSourcesByName.size() == 1) {
            dataSourceName = dataSourcesByName.keySet().iterator().next();
        }
        else if (hasProperty(DEFAULT_DATA_SOURCE_NAME_PROPERTY)) {
            String possibleDataSourceName = getProperty(DEFAULT_DATA_SOURCE_NAME_PROPERTY);
            if (dataSourcesByName.containsKey(possibleDataSourceName)) {
                dataSourceName = possibleDataSourceName;
            }
            else if (dataSourcesByName.containsKey(DEFAULT_DATA_SOURCE_NAME)) {
                dataSourceName = DEFAULT_DATA_SOURCE_NAME;
            }
        }
        return dataSourceName;
    }

    public static Connection getConnection() {
        String dataSourceName = getDefaultSourceName();
        if (dataSourceName == null) {
            throw new RuntimeException("No data source found !!");
        }
        return getConnection(dataSourceName);
    }

    public static Connection getConnection(String dataSourceName) {
        DataSource source = dataSourcesByName.get(dataSourceName);
        if (source == null) {
            throw new RuntimeException("No data source with name \"" + dataSourceName + "\"");
        }
        Connection connection = null;
        try {
            connection = source.getConnection();
        }
        catch (Exception ex) {}
        return connection;
    }
}
