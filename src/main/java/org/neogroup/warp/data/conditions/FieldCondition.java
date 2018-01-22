package org.neogroup.warp.data.conditions;

/**
 *
 */
public abstract class FieldCondition extends Condition {

    private final String field;

    /**
     *
     * @param field
     */
    public FieldCondition(String field) {
        this.field = field;
    }

    /**
     *
     * @return
     */
    public String getField() {
        return field;
    }
}
