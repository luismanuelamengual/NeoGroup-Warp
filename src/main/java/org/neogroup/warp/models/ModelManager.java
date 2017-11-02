package org.neogroup.warp.models;

import java.util.Collection;

public abstract class ModelManager<M extends Object> {

    public static final String DEFAULT_ID_PROPERTY_NAME = "id";

    /**
     * Retrieve the name of the id property of the model
     * @return name of the id property
     */
    protected String getIdPropertyName () {
        return DEFAULT_ID_PROPERTY_NAME;
    }

    /**
     * Retrieve a model by id
     * @param id id to retrieve the model
     * @param params parameters
     * @return model
     */
    public M retrieve (Object id, Object... params) {
        ModelQuery query = new ModelQuery();
        query.addFilter(getIdPropertyName(), id);
        query.setLimit(1);
        Collection<M> models = retrieve(query, params);
        M model = null;
        if (!models.isEmpty()) {
            model = models.iterator().next();
        }
        return model;
    }

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
     * Deletes a model
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
