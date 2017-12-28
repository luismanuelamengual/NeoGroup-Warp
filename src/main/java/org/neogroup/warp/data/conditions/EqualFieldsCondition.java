package org.neogroup.warp.data.conditions;

public class EqualFieldsCondition extends FieldCondition {

    private final String field2;

    public EqualFieldsCondition(String field, String field2) {
        super(field);
        this.field2 = field2;
    }

    public String getField2() {
        return field2;
    }
}
