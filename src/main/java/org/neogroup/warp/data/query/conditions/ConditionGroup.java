package org.neogroup.warp.data.query.conditions;

import org.neogroup.warp.data.query.fields.Field;
import org.neogroup.warp.data.query.fields.RawField;

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

    public void clearConditions () {
        conditions.clear();
    }

    public ConditionGroup addCondition (String rawField, Object value) {
        return addCondition(new RawField(rawField), value);
    }

    public ConditionGroup addCondition (String rawField, Operator operator, Object value) {
        return addCondition(new RawField(rawField), operator, value);
    }

    public ConditionGroup addCondition (Field field, Object value) {
        return addCondition(new OperationCondition(field, value));
    }

    public ConditionGroup addCondition (Field field, Operator operator, Object value) {
        return addCondition(new OperationCondition(field, operator, value));
    }

    public ConditionGroup addCondition (Condition condition) {
        conditions.add(condition);
        return this;
    }
}
