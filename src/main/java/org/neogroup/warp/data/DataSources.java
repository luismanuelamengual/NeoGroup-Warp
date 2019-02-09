package org.neogroup.warp.data;

import java.util.HashMap;
import java.util.Map;

import static org.neogroup.warp.Warp.*;

public abstract class DataSources {

    private static final String DEFAULT_DATA_SOURCE_NAME_PROPERTY = "defaultDatasourceName";
    private static final String DEFAULT_DATA_SOURCE_NAME = "main";

    private static final Map<String, DataSource> dataSourcesByName;

    static {
        dataSourcesByName = new HashMap<>();
    }

    public static void registerDataSource(Class<? extends DataSource> dataSourceClass) {
        try {
            DataSource dataConnection = dataSourceClass.getConstructor().newInstance();
            DataSourceComponent dataSourceComponent = dataSourceClass.getAnnotation(DataSourceComponent.class);
            String dataSourceName = null;
            if (dataSourceComponent != null) {
                dataSourceName = dataSourceComponent.value();
                dataSourcesByName.put(dataSourceName, dataConnection);
            }
            getLogger().info("Data source \"" + dataSourceClass.getName() + "\" registered !!" + (dataSourceName != null ? " [name=" + dataSourceName + "]" : ""));
        } catch (Exception ex) {
            throw new RuntimeException("Error registering data manager \"" + dataSourceClass.getName() + "\" !!", ex);
        }
    }

    public static DataConnection getConnection() {
        DataConnection connection = null;
        if (!dataSourcesByName.isEmpty()) {
            DataSource source = null;
            if (dataSourcesByName.size() == 1) {
                source = dataSourcesByName.values().iterator().next();
            }
            else {
                String dataSourceName = null;
                if (hasProperty(DEFAULT_DATA_SOURCE_NAME_PROPERTY)) {
                    dataSourceName = getProperty(DEFAULT_DATA_SOURCE_NAME_PROPERTY);
                }
                else {
                    dataSourceName = DEFAULT_DATA_SOURCE_NAME;
                }
                source = dataSourcesByName.get(dataSourceName);
                if (source == null) {
                    throw new RuntimeException("No data source with name \"" + dataSourceName + "\"");
                }
            }
            connection = source.getConnection();
        }
        return connection;
    }

    public static DataConnection getConnection(String dataSourceName) {
        DataSource source = dataSourcesByName.get(dataSourceName);
        if (source == null) {
            throw new RuntimeException("No data source with name \"" + dataSourceName + "\"");
        }
        return source.getConnection();
    }
}
