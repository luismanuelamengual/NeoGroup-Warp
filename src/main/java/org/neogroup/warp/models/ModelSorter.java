
package org.neogroup.warp.models;

/**
 * Entity sorter
 */
public class ModelSorter {

    private final String property;
    private final ModelSorterDirection direction;

    /**
     * Constructor of the entity sorter
     * @param property property to sort
     */
    public ModelSorter(String property) {
        this(property, ModelSorterDirection.ASC);
    }

    /**
     * Constructor of the entity sorter
     * @param property property of sort
     * @param direction direction of sorting
     */
    public ModelSorter(String property, ModelSorterDirection direction) {
        this.property = property;
        this.direction = direction;
    }

    /**
     * Get the property of the entity
     * @return String property
     */
    public String getProperty() {
        return property;
    }

    /**
     * Get the direction of sorting for the entity
     * @return sorter direction
     */
    public ModelSorterDirection getDirection() {
        return direction;
    }
}
