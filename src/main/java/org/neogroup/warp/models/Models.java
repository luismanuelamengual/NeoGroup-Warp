package org.neogroup.warp.models;

import org.neogroup.warp.WarpInstance;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Models {

    private final WarpInstance warpInstance;
    private final Map<Class, ModelManager> managers;
    private final Map<Class, ModelManager> managersByModelClass;

    /**
     *
     */
    public Models(WarpInstance warpInstance) {
        this.warpInstance = warpInstance;
        this.managers = new HashMap<>();
        this.managersByModelClass = new HashMap<>();
    }

    /**
     *
     * @param modelManagerClass
     */
    public void registerModelManager (Class<? extends ModelManager> modelManagerClass) {

        try {
            ModelManager modelManager = modelManagerClass.getConstructor().newInstance();
            Type type = modelManagerClass.getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type[] fieldArgTypes = parameterizedType.getActualTypeArguments();
                Class modelClass = (Class) fieldArgTypes[0];
                managersByModelClass.put(modelClass, modelManager);
            }
            managers.put(modelManagerClass, modelManager);
            warpInstance.getLogger().info("Model manager \"" + modelManagerClass.getName() + "\" registered !!");
        }
        catch (Exception ex) {
            throw new RuntimeException ("Error registering model manager \"" + modelManagerClass.getName() + "\" !!", ex);
        }
    }

    /**
     *
     * @param modelManagerClass
     * @param <M>
     * @return
     */
    public <M extends ModelManager> M getModelManager (Class<? extends M> modelManagerClass) {
        return (M)managers.get(modelManagerClass);
    }

    /**
     *
     * @param model
     * @param params
     * @param <M>
     * @return
     */
    public <M extends Object> M createModel(M model, Object... params) {
        return (M) managersByModelClass.get(model.getClass()).create(model, params);
    }

    /**
     *
     * @param model
     * @param params
     * @param <M>
     * @return
     */
    public <M extends Object> M updateModel(M model, Object... params) {
        return (M) managersByModelClass.get(model.getClass()).update(model, params);
    }

    /**
     *
     * @param model
     * @param params
     * @param <M>
     * @return
     */
    public <M extends Object> M deleteModel(M model, Object... params) {
        return (M) managersByModelClass.get(model.getClass()).delete(model, params);
    }

    /**
     *
     * @param modelClass
     * @param id
     * @param params
     * @param <M>
     * @return
     */
    public <M extends Object> M retrieveModel(Class<? extends M> modelClass, Object id, Object... params) {
        return (M) managersByModelClass.get(modelClass).retrieve(id, params);
    }

    /**
     *
     * @param modelClass
     * @param params
     * @param <M>
     * @return
     */
    public <M extends Object> Collection<M> retrieveModels (Class<? extends M> modelClass, Object... params) {
        return retrieveModels(modelClass, null, params);
    }

    /**
     *
     * @param modelClass
     * @param query
     * @param params
     * @param <M>
     * @return
     */
    public <M extends Object> Collection<M> retrieveModels (Class<? extends M> modelClass, ModelQuery query, Object... params) {
        return (Collection<M>) managersByModelClass.get(modelClass).retrieve(query, params);
    }
}
