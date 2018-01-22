package org.neogroup.warp.data.conditions;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ConditionGroup extends Condition {

    private ConditionGroupConnector connector;
    private final List<Condition> conditions;

    /**
     *
     */
    public ConditionGroup() {
        this(ConditionGroupConnector.AND);
    }

    /**
     *
     * @param connector
     */
    public ConditionGroup(ConditionGroupConnector connector) {
        this.connector = connector;
        conditions = new ArrayList<>();
    }

    /**
     *
     * @return
     */
    public ConditionGroupConnector getConnector() {
        return connector;
    }

    /**
     *
     * @param connector
     */
    public void setConnector(ConditionGroupConnector connector) {
        this.connector = connector;
    }

    /**
     *
     * @return
     */
    public List<Condition> getConditions() {
        return conditions;
    }

    /**
     *
     */
    public void clearConditions () {
        conditions.clear();
    }

    /**
     *
     * @param field
     * @param value
     * @return
     */
    public ConditionGroup addCondition (String field, Object value) {
        return addCondition(new FieldOperationCondition(field, value));
    }

    /**
     *
     * @param field
     * @param operator
     * @param value
     * @return
     */
    public ConditionGroup addCondition (String field, Operator operator, Object value) {
        return addCondition(new FieldOperationCondition(field, operator, value));
    }

    /**
     *
     * @param rawCondition
     * @return
     */
    public ConditionGroup addCondition (String rawCondition) {
        return addCondition(new RawCondition(rawCondition));
    }

    /**
     *
     * @param condition
     * @return
     */
    public ConditionGroup addCondition (Condition condition) {
        conditions.add(condition);
        return this;
    }
}
