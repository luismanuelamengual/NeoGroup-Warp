package org.neogroup.warp.data;

import org.neogroup.warp.Warp;

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

    public <D extends DataManager> D getDataManager() {

        DataManager dataManager = null;
        if (!dataManagers.isEmpty()) {

            if (Warp.hasProperty(DEFAULT_DATAMANAER_NAME_PROPERTY_NAME)) {
                dataManager = dataManagersByName.get(Warp.getProperty(DEFAULT_DATAMANAER_NAME_PROPERTY_NAME));
            }
            else {
                dataManager = dataManagers.values().iterator().next();
            }
        }
        return (D)dataManager;
    }

    public <D extends DataManager> D getDataManager(String dataManagerName) {

        return (D)dataManagersByName.get(dataManagerName);
    }

    public <D extends DataManager> D getDataManager(Class<? extends DataManager> dataManagerClass) {
        return (D)dataManagers.get(dataManagerClass);
    }
}
