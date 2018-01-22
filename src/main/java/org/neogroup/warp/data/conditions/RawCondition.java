package org.neogroup.warp.data.conditions;

/**
 *
 */
public class RawCondition extends Condition {

    private final String condition;

    /**
     *
     * @param condition
     */
    public RawCondition(String condition) {
        this.condition = condition;
    }

    /**
     *
     * @return
     */
    public String getCondition() {
        return condition;
    }
}
