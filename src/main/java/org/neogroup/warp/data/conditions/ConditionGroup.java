package org.neogroup.warp.data.conditions;

import java.util.ArrayList;
import java.util.List;

public class ConditionGroup extends Condition {

    private ConditionGroupConnector connector;
    private final List<Condition> conditions;

    public ConditionGroup() {
        this(ConditionGroupConnector.AND);
    }

    public ConditionGroup(ConditionGroupConnector connector) {
        this.connector = connector;
        conditions = new ArrayList<>();
    }

    public ConditionGroupConnector getConnector() {
        return connector;
    }

    public void setConnector(ConditionGroupConnector connector) {
        this.connector = connector;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void clearConditions () {
        conditions.clear();
    }

    public ConditionGroup addCondition (String field, Object value) {
        return addCondition(new FieldOperationCondition(field, value));
    }

    public ConditionGroup addCondition (String field, Operator operator, Object value) {
        return addCondition(new FieldOperationCondition(field, operator, value));
    }

    public ConditionGroup addCondition (String rawCondition) {
        return addCondition(new RawCondition(rawCondition));
    }

    public ConditionGroup addCondition (Condition condition) {
        conditions.add(condition);
        return this;
    }
}
