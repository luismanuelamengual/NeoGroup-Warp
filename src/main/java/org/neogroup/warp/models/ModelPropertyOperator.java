
package org.neogroup.warp.models;

/**
 * Operator for property filters
 */
public abstract class ModelPropertyOperator {

    public static final String EQUALS = "eq";
    public static final String DISTINCT = "dt";
    public static final String CONTAINS = "ct";
    public static final String NOT_CONTAINS = "nct";
    public static final String IN = "in";
    public static final String NOT_IN = "nin";
    public static final String GREATER_THAN = "gt";
    public static final String GREATER_OR_EQUALS_THAN = "gte";
    public static final String LESS_THAN = "lt";
    public static final String LESS_OR_EQUALS_THAN = "lte";
}
