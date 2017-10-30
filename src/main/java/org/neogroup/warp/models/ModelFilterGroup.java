
package org.neogroup.warp.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Group of model filters
 */
public class ModelFilterGroup extends ModelFilter {

    private ModelFilterGroupConnector connector;
    private final List<ModelFilter> filters;

    /**
     * Constructor for the group of filters
     */
    public ModelFilterGroup() {
        filters = new ArrayList<>();
        connector = ModelFilterGroupConnector.AND;
    }

    /**
     * Get the connector of this group of filters
     * @return EntityFilterGroupConnector connector
     */
    public ModelFilterGroupConnector getConnector() {
        return connector;
    }

    /**
     * Set the connector for this group of filters
     * @param connector connector
     */
    public void setConnector(ModelFilterGroupConnector connector) {
        this.connector = connector;
    }

    /**
     * Get the filters of this group
     * @return list of filters
     */
    public List<ModelFilter> getFilters() {
        return filters;
    }

    /**
     * Get the amount of filters of this group
     * @return int
     */
    public int getSize() {
        return filters.size();
    }

    /**
     * Indicates if this group is empty
     * @return boolean
     */
    public boolean isEmpty() {
        return filters.isEmpty();
    }

    /**
     * Add a new filter to the group
     * @param filter filter
     */
    public void addFilter (ModelFilter filter) {
        filters.add(filter);
    }

    /**
     * Removes a filter from the group
     * @param filter filter
     */
    public void removeFilter (ModelFilter filter) {
        filters.remove(filter);
    }
}
