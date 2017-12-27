package org.neogroup.warp.data;

import org.neogroup.warp.Warp;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class DataManagers {

    private static final String DEFAULT_DATAMANAER_NAME_PROPERTY_NAME = "default_datamanger_name";

    private final Map<Class, DataManager> dataManagers;
    private final Map<String, DataManager> dataManagersByName;

    public DataManagers() {
        dataManagers = new HashMap<>();
        dataManagersByName = new HashMap<>();
    }

    public void registerDataManager(Class<? extends DataManager> dataManagerClass) {

        try {
            DataManager dataManager = dataManagerClass.getConstructor().newInstance();
            dataManagers.put(dataManagerClass, dataManager);

            DataManagerComponent dataManagerComponent = dataManagerClass.getAnnotation(DataManagerComponent.class);
            if (dataManagerComponent != null) {
                dataManagersByName.put(dataManagerComponent.name(), dataManager);
            }
        }
        catch (Exception ex) {
            throw new DataException ("Error registering data manager \"" + dataManagerClass.getName() + "\" !!", ex);
        }
    }

    public DataManager getDataManager(String dataManagerName) {

        return dataManagersByName.get(dataManagerName);
    }

    public DataManager getDataManager(Class<? extends DataManager> dataManagerClass) {
        return dataManagers.get(dataManagerClass);
    }

    public DataManager getDefaultDataManager() {

        DataManager dataManager = null;
        if (!dataManagers.isEmpty()) {

            if (Warp.hasProperty(DEFAULT_DATAMANAER_NAME_PROPERTY_NAME)) {
                dataManager = dataManagersByName.get(Warp.getProperty(DEFAULT_DATAMANAER_NAME_PROPERTY_NAME));
            }
            else {
                dataManager = dataManagers.values().iterator().next();
            }
        }
        return dataManager;
    }

    public Connection getConnection () {

        DataManager dataManager = getDefaultDataManager();
        if (dataManager == null) {
            throw new DataException("No default data manager was registered");
        }
        return getConnection(dataManager);
    }

    public Connection getConnection (String dataManagerName) {

        DataManager dataManager = getDataManager(dataManagerName);
        if (dataManager == null) {
            throw new DataException("Data manager with name \"" + dataManagerName + "\" was not registered");
        }
        return getConnection(dataManager);
    }

    public Connection getConnection (DataManager dataManager) {

        try {
            return dataManager.getConnection();
        }
        catch (Exception exception) {
            throw new DataException("Error retrieving data connection !!", exception);
        }
    }
}
