package org.neogroup.warp.data.conditions;

public abstract class FieldCondition extends Condition {

    private final String field;

    public FieldCondition(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
