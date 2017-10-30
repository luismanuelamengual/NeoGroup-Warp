package org.neogroup.warp.models;

public abstract class CustomModelManager extends ModelManager<CustomModel> {

    private final String modelName;

    public CustomModelManager(String modelName) {
        this.modelName = modelName;
    }

    public String getModelName() {
        return modelName;
    }
}
