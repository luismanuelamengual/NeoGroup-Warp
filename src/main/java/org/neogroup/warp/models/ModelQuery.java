package org.neogroup.warp.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity query
 */
public class ModelQuery {

    private final ModelFilterGroup filters;
    private final List<ModelSorter> sorters;
    private Integer start;
    private Integer limit;

    /**
     * Constructor for the entity query
     */
    public ModelQuery() {
        this.filters = new ModelFilterGroup();
        this.sorters = new ArrayList<>();
        this.start = null;
        this.limit = null;
    }

    /**
     * Get the filters of the query
     * @return group of filters
     */
    public ModelFilterGroup getFilters() {
        return filters;
    }

    /**
     * Get the sorters of the query
     * @return list of sorters
     */
    public List<ModelSorter> getSorters() {
        return sorters;
    }

    /**
     * Get the offset for entities
     * @return int offset
     */
    public Integer getStart() {
        return start;
    }

    /**
     * Sets the offset for entities
     * @param start offset
     */
    public void setStart(Integer start) {
        this.start = start;
    }

    /**
     * Get the limit of entities
     * @return int limit
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * Set the limit for entities
     * @param limit limit
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * Set the connector for the group of filters
     * @param connector connector for filters
     */
    public void setFiltersConnector (ModelFilterGroupConnector connector) {
        filters.setConnector(connector);
    }

    /**
     * Get the connector of the filters
     * @return EntityFilterGroupConnector connector
     */
    public ModelFilterGroupConnector getFiltersConnector() {
        return filters.getConnector();
    }

    /**
     * Adds a new filter to the query
     * @param filter filter
     */
    public void addFilter (ModelFilter filter) {
        filters.addFilter(filter);
    }

    /**
     * Adds a new property filter to the query
     * @param property property of entity
     * @param value value for entity
     * @return EntityPropertyFilter created filter
     */
    public ModelPropertyFilter addFilter (String property, Object value) {
        ModelPropertyFilter filter = new ModelPropertyFilter(property, value);
        addFilter(filter);
        return filter;
    }

    /**
     * Adds a new property filter
     * @param property property of entity
     * @param operator operator
     * @param value value of property
     * @return EntityPropertyFilter created filter
     */
    public ModelPropertyFilter addFilter (String property, String operator, Object value) {
        ModelPropertyFilter filter = new ModelPropertyFilter(property, operator, value);
        addFilter(filter);
        return filter;
    }

    /**
     * Removes a filter from the query
     * @param filter filter
     */
    public void removeFilter (ModelFilter filter) {
        filters.removeFilter(filter);
    }

    /**
     * Adds a new sorter to the query
     * @param sorter sorter
     */
    public void addSorter (ModelSorter sorter) {
        sorters.add(sorter);
    }

    /**
     * Adds a new sorter to the query
     * @param property property of entity
     * @return EntitySorter created sorter
     */
    public ModelSorter addSorter (String property) {
        ModelSorter sorter = new ModelSorter(property);
        addSorter(sorter);
        return sorter;
    }

    /**
     * Adds a new sorter to the query
     * @param property property of entity
     * @param direction direction of sort
     * @return EntitySorter created sorter
     */
    public ModelSorter addSorter (String property, ModelSorterDirection direction) {
        ModelSorter sorter = new ModelSorter(property, direction);
        addSorter(sorter);
        return sorter;
    }

    /**
     * Removes a sorter from the query
     * @param sorter sorter
     */
    public void removeSorter (ModelSorter sorter) {
        sorters.remove(sorter);
    }
}
