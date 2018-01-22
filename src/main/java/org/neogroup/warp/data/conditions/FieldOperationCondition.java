package org.neogroup.warp.data.conditions;

/**
 *
 */
public class FieldOperationCondition extends FieldCondition {

    private final Operator operator;
    private final Object value;

    /**
     *
     * @param field
     * @param value
     */
    public FieldOperationCondition(String field, Object value) {
        this(field, Operator.EQUALS, value);
    }

    /**
     *
     * @param field
     * @param operator
     * @param value
     */
    public FieldOperationCondition(String field, Operator operator, Object value) {
        super(field);
        this.operator = operator;
        this.value = value;
    }

    /**
     *
     * @return
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     *
     * @return
     */
    public Object getValue() {
        return value;
    }
}
