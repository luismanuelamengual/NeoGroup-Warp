package org.neogroup.warp.data;

import org.neogroup.warp.Warp;

import java.util.HashMap;
import java.util.Map;

public class DataConnections {

    private static final String DEFAULT_DATA_CONNECTION_NAME_PROPERTY_NAME = "default_data_connection_name";

    private final Map<Class, DataConnection> dataConnections;
    private final Map<String, DataConnection> dataConnectionsByName;

    public DataConnections() {
        dataConnections = new HashMap<>();
        dataConnectionsByName = new HashMap<>();
    }

    public void registerConnection(Class<? extends DataConnection> dataConnectionClass) {

        try {
            DataConnection dataConnection = dataConnectionClass.getConstructor().newInstance();
            dataConnections.put(dataConnectionClass, dataConnection);

            DataConnectionComponent dataConnectionComponent = dataConnectionClass.getAnnotation(DataConnectionComponent.class);
            if (dataConnectionComponent != null) {
                dataConnectionsByName.put(dataConnectionComponent.name(), dataConnection);
            }
        }
        catch (Exception ex) {
            throw new DataException ("Error registering data manager \"" + dataConnectionClass.getName() + "\" !!", ex);
        }
    }

    public <D extends DataConnection> D getConnection() {

        DataConnection dataConnection = null;
        if (!dataConnections.isEmpty()) {

            if (Warp.hasProperty(DEFAULT_DATA_CONNECTION_NAME_PROPERTY_NAME)) {
                dataConnection = dataConnectionsByName.get(Warp.getProperty(DEFAULT_DATA_CONNECTION_NAME_PROPERTY_NAME));
            }
            else {
                dataConnection = dataConnections.values().iterator().next();
            }
        }
        return (D) dataConnection;
    }

    public <D extends DataConnection> D getConnection(String dataConnectionName) {

        return (D) dataConnectionsByName.get(dataConnectionName);
    }

    public <D extends DataConnection> D getConnection(Class<? extends DataConnection> dataConnectionClass) {
        return (D) dataConnections.get(dataConnectionClass);
    }
}
