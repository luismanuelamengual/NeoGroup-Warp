
package org.neogroup.warp.models;

/**
 * Property filter for models
 */
public class ModelPropertyFilter extends ModelFilter {

    private final String property;
    private final String operator;
    private final Object value;

    /**
     * Constructor for property filter
     * @param property model property
     * @param value value
     */
    public ModelPropertyFilter(String property, Object value) {
        this(property, ModelPropertyOperator.EQUALS, value);
    }

    /**
     * Constructor for property filter
     * @param property model property
     * @param operator operator
     * @param value value
     */
    public ModelPropertyFilter(String property, String operator, Object value) {
        this.property = property;
        this.operator = operator;
        this.value = value;
    }

    /**
     * Get the name of the property of the entity
     * @return String name of property
     */
    public String getProperty() {
        return property;
    }

    /**
     * Get the operator of the filter
     * @return String name of operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Get the value of the filter
     * @return Object value of filter
     */
    public Object getValue() {
        return value;
    }
}
