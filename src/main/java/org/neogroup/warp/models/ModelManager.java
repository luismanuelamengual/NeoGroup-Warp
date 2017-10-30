package org.neogroup.warp.models;

import java.util.Collection;
import java.util.Map;

public abstract class ModelManager<M extends Object> {

    /**
     * Creates a model
     * @param model model to create
     * @param params parameters
     * @return created model
     */
    protected abstract M create (M model, Map<String,Object> params);

    /**
     * Updates a model
     * @param model model to update
     * @param params parameters
     * @return updated model
     */
    protected abstract M update (M model, Map<String,Object> params);

    /**
     * Deletes an model
     * @param model model to delete
     * @param params parameters
     * @return deleted model
     */
    protected abstract M delete (M model, Map<String,Object> params);

    /**
     * Retrieves a collection of models
     * @param query query of models
     * @param params parameters
     * @return collection of models
     */
    protected abstract Collection<M> retrieve (ModelQuery query, Map<String,Object> params);
}
