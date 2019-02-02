package org.neogroup.warp.query.conditions;

import org.neogroup.warp.query.fields.Field;

public class BasicCondition extends Condition {

    private Field field;
    private ConditionOperator operator;
    private Object value;

    public BasicCondition(String field, Object value) {
        this(new Field(field), ConditionOperator.EQUALS, value);
    }

    public BasicCondition(Field field, Object value) {
        this(field, ConditionOperator.EQUALS, value);
    }

    public BasicCondition(String field, ConditionOperator operator) {
        this(new Field(field), operator, null);
    }

    public BasicCondition(Field field, ConditionOperator operator) {
        this(field, operator, null);
    }

    public BasicCondition(String field, ConditionOperator operator, Object value) {
        this(new Field(field), operator, value);
    }

    public BasicCondition(Field field, ConditionOperator operator, Object value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    public Field getField() {
        return field;
    }

    public ConditionOperator getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }
}