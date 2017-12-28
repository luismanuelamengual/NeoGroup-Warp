package org.neogroup.warp.data.joins;

import org.neogroup.warp.data.conditions.Condition;
import org.neogroup.warp.data.conditions.ConditionGroup;
import org.neogroup.warp.data.conditions.ConditionGroupConnector;
import org.neogroup.warp.data.conditions.EqualFieldsCondition;

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

    public Join(String tableName, JoinType joinType, String rawLeftField, String rawRightField) {
        this(tableName, joinType);
        addCondition(new EqualFieldsCondition(rawLeftField, rawRightField));
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

    public Join addCondition(Condition condition) {
        conditionGroup.addCondition(condition);
        return this;
    }
}
