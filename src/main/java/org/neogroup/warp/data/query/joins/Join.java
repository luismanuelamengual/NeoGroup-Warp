package org.neogroup.warp.data.query.joins;

import org.neogroup.warp.data.query.conditions.*;
import org.neogroup.warp.data.query.fields.Field;
import org.neogroup.warp.data.query.fields.RawField;

public class Join {

    private final String tableName;
    private final JoinType joinType;
    private final ConditionGroup conditionGroup;

    public Join(String tableName) {
        this(tableName, JoinType.INNER_JOIN);
    }

    public Join(String tableName, JoinType joinType) {
        this.tableName = tableName;
        this.joinType = joinType;
        this.conditionGroup = new ConditionGroup();
    }

    public Join(String tableName, String rawLeftField, String rawRightField) {
        this(tableName, JoinType.INNER_JOIN, rawLeftField, rawRightField);
    }

    public Join(String tableName, Field leftField, Field rightField) {
        this(tableName, JoinType.INNER_JOIN, leftField, rightField);
    }

    public Join(String tableName, JoinType joinType, String rawLeftField, String rawRightField) {
        this(tableName, joinType, new RawField(rawLeftField), new RawField(rawRightField));
    }

    public Join(String tableName, JoinType joinType, Field leftField, Field rightField) {
        this(tableName, joinType);
        conditionGroup.addCondition(new OperationCondition(leftField, rightField));
    }

    public JoinType getJoinType() {
        return joinType;
    }

    public String getTableName() {
        return tableName;
    }

    public ConditionGroup getConditions() {
        return conditionGroup;
    }

    public ConditionGroupConnector getConnector() {
        return conditionGroup.getConnector();
    }

    public Join setConnector(ConditionGroupConnector connector) {
        conditionGroup.setConnector(connector);
        return this;
    }

    public Join clearConditions() {
        conditionGroup.clearConditions();
        return this;
    }

    public Join addCondition(String rawField, Object value) {
        conditionGroup.addCondition(rawField, value);
        return this;
    }

    public Join addCondition(String rawField, Operator operator, Object value) {
        conditionGroup.addCondition(rawField, operator, value);
        return this;
    }

    public Join addCondition(Field field, Object value) {
        conditionGroup.addCondition(field, value);
        return this;
    }

    public Join addCondition(Field field, Operator operator, Object value) {
        conditionGroup.addCondition(field, operator, value);
        return this;
    }

    public Join addCondition(String rawCondition) {
        conditionGroup.addCondition(rawCondition);
        return this;
    }

    public Join addCondition(Condition condition) {
        conditionGroup.addCondition(condition);
        return this;
    }
}
