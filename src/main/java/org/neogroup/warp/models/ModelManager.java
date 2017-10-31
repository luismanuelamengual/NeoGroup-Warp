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
    public abstract M create (M model, Object... params);

    /**
     * Updates a model
     * @param model model to update
     * @param params parameters
     * @return updated model
     */
    public abstract M update (M model, Object... params);

    /**
     * Deletes an model
     * @param model model to delete
     * @param params parameters
     * @return deleted model
     */
    public abstract M delete (M model, Object... params);

    /**
     * Retrieves a collection of models
     * @param query query of models
     * @param params parameters
     * @return collection of models
     */
    public abstract Collection<M> retrieve (ModelQuery query, Object... params);
}
