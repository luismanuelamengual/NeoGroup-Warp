package org.neogroup.warp.models;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Models {

    private final Map<Class, ModelManager> managers;
    private final Map<Class, ModelManager> managersByModelClass;

    public Models() {
        this.managers = new HashMap<>();
        this.managersByModelClass = new HashMap<>();
    }

    public void registerModelManager (ModelManager modelManager) {

        Class modelManagerClass = modelManager.getClass();
        Type type = modelManagerClass.getGenericSuperclass();
        if(type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] fieldArgTypes = parameterizedType.getActualTypeArguments();
            Class modelClass = (Class)fieldArgTypes[0];
            managersByModelClass.put(modelClass, modelManager);
        }
        managers.put(modelManagerClass, modelManager);
    }

    public <M extends ModelManager> M getModelManager (Class<? extends M> modelManagerClass) {
        return (M)managers.get(modelManagerClass);
    }

    public <M extends Object> M createModel(M model, Object... params) {
        return (M) managersByModelClass.get(model.getClass()).create(model, params);
    }

    public <M extends Object> M updateModel(M model, Object... params) {
        return (M) managersByModelClass.get(model.getClass()).update(model, params);
    }

    public <M extends Object> M deleteModel(M model, Object... params) {
        return (M) managersByModelClass.get(model.getClass()).delete(model, params);
    }

    public <M extends Object> M retrieveModel(Class<? extends M> modelClass, Object id, Object... params) {
        return (M) managersByModelClass.get(modelClass).retrieve(id, params);
    }

    public <M extends Object> Collection<M> retrieveModels (Class<? extends M> modelClass, Object... params) {
        return retrieveModels(modelClass, null, params);
    }

    public <M extends Object> Collection<M> retrieveModels (Class<? extends M> modelClass, ModelQuery query, Object... params) {
        return (Collection<M>) managersByModelClass.get(modelClass).retrieve(query, params);
    }
}
