package org.neogroup.warp.query.conditions;

import org.neogroup.warp.query.Field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConditionGroup extends Condition {

    private List<Condition> conditions;
    private ConditionGroupConnector connector;

    public ConditionGroup() {
        this(ConditionGroupConnector.AND);
    }

    public ConditionGroup(ConditionGroupConnector connector) {
        conditions = new ArrayList<>();
        this.connector = connector;
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

    public ConditionGroup clearConditions() {
        conditions.clear();
        return this;
    }

    public boolean isEmpty() {
        return conditions.isEmpty();
    }

    public ConditionGroup addCondition(Condition condition) {
        conditions.add(condition);
        return this;
    }

    public ConditionGroup on(String field, Object value) {
        return addCondition(new BasicCondition(field, value));
    }

    public ConditionGroup on(Field field, Object value) {
        return addCondition(new BasicCondition(field, value));
    }

    public ConditionGroup on(String field, ConditionOperator operator, Object value) {
        return addCondition(new BasicCondition(field, operator, value));
    }

    public ConditionGroup on(Field field, ConditionOperator operator, Object value) {
        return addCondition(new BasicCondition(field, operator, value));
    }

    public ConditionGroup onGroup(ConditionGroup condition) {
        return addCondition(condition);
    }

    public ConditionGroup onRaw(RawCondition condition) {
        return addCondition(condition);
    }

    public ConditionGroup onField(String field1, String field2) {
        return onField(new Field(field1), ConditionOperator.EQUALS, new Field(field2));
    }

    public ConditionGroup onField(Field field1, Field field2) {
        return onField(field1, ConditionOperator.EQUALS, field2);
    }

    public ConditionGroup onField(String field1, ConditionOperator operator, String field2) {
        return onField(new Field(field1), operator, new Field(field2));
    }

    public ConditionGroup onField(Field field1, ConditionOperator operator, Field field2) {
        return addCondition(new BasicCondition(field1, operator, field2));
    }

    public ConditionGroup onNull (String field) {
        return addCondition(new BasicCondition(field, ConditionOperator.NULL));
    }

    public ConditionGroup onNull (Field field) {
        return addCondition(new BasicCondition(field, ConditionOperator.NULL));
    }

    public ConditionGroup onNotNull (String field) {
        return addCondition(new BasicCondition(field, ConditionOperator.NOT_NULL));
    }

    public ConditionGroup onNotNull (Field field) {
        return addCondition(new BasicCondition(field, ConditionOperator.NOT_NULL));
    }

    public ConditionGroup onIn (String field, Collection<Object> values) {
        return addCondition(new BasicCondition(field, ConditionOperator.IN, values));
    }

    public ConditionGroup onIn (Field field, Collection<Object> values) {
        return addCondition(new BasicCondition(field, ConditionOperator.IN, values));
    }

    public ConditionGroup onNotIn (String field, Collection<Object> values) {
        return addCondition(new BasicCondition(field, ConditionOperator.NOT_IN, values));
    }

    public ConditionGroup onNotIn (Field field, Collection<Object> values) {
        return addCondition(new BasicCondition(field, ConditionOperator.NOT_IN, values));
    }

    public ConditionGroup onContains (String field, Object value) {
        return addCondition(new BasicCondition(field, ConditionOperator.CONTAINS, value));
    }

    public ConditionGroup onContains (Field field, Object value) {
        return addCondition(new BasicCondition(field, ConditionOperator.CONTAINS, value));
    }

    public ConditionGroup onNotContains (String field, Object value) {
        return addCondition(new BasicCondition(field, ConditionOperator.NOT_CONTAINS, value));
    }

    public ConditionGroup onNotContains (Field field, Object value) {
        return addCondition(new BasicCondition(field, ConditionOperator.NOT_CONTAINS, value));
    }

    public ConditionGroup onGreaterThan (String field, Object value) {
        return on(field, ConditionOperator.GREATER_THAN, value);
    }

    public ConditionGroup onGreaterThan (Field field, Object value) {
        return on(field, ConditionOperator.GREATER_THAN, value);
    }

    public ConditionGroup onGreaterOrEqualsThan (String field, Object value) {
        return on(field, ConditionOperator.GREATER_OR_EQUALS_THAN, value);
    }

    public ConditionGroup onGreaterOrEqualsThan (Field field, Object value) {
        return on(field, ConditionOperator.GREATER_OR_EQUALS_THAN, value);
    }

    public ConditionGroup onLowerThan (String field, Object value) {
        return on(field, ConditionOperator.LESS_THAN, value);
    }

    public ConditionGroup onLowerThan (Field field, Object value) {
        return on(field, ConditionOperator.LESS_THAN, value);
    }

    public ConditionGroup onLowerOrEqualsThan (String field, Object value) {
        return on(field, ConditionOperator.LESS_OR_EQUALS_THAN, value);
    }

    public ConditionGroup onLowerOrEqualsThan (Field field, Object value) {
        return on(field, ConditionOperator.LESS_OR_EQUALS_THAN, value);
    }
}