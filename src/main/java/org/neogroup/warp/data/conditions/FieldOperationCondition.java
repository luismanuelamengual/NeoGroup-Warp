package org.neogroup.warp.data.conditions;

public class FieldOperationCondition extends FieldCondition {

    private final Operator operator;
    private final Object value;

    public FieldOperationCondition(String field, Object value) {
        this(field, Operator.EQUALS, value);
    }

    public FieldOperationCondition(String field, Operator operator, Object value) {
        super(field);
        this.operator = operator;
        this.value = value;
    }

    public Operator getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }
}
