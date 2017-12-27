package org.neogroup.warp.data.query.conditions;

public class RawCondition extends Condition {

    private final String condition;

    public RawCondition(String condition) {
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }
}
