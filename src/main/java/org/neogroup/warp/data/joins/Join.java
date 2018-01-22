package org.neogroup.warp.data.joins;

import org.neogroup.warp.data.RawValue;
import org.neogroup.warp.data.conditions.Condition;
import org.neogroup.warp.data.conditions.ConditionGroup;
import org.neogroup.warp.data.conditions.ConditionGroupConnector;
import org.neogroup.warp.data.conditions.FieldOperationCondition;

/**
 *
 */
public class Join {

    private final String tableName;
    private final JoinType joinType;
    private final ConditionGroup conditionGroup;

    /**
     *
     * @param tableName
     */
    public Join(String tableName) {
        this(tableName, JoinType.INNER_JOIN);
    }

    /**
     *
     * @param tableName
     * @param joinType
     */
    public Join(String tableName, JoinType joinType) {
        this.tableName = tableName;
        this.joinType = joinType;
        this.conditionGroup = new ConditionGroup();
    }

    /**
     *
     * @param tableName
     * @param rawLeftField
     * @param rawRightField
     */
    public Join(String tableName, String rawLeftField, String rawRightField) {
        this(tableName, JoinType.INNER_JOIN, rawLeftField, rawRightField);
    }

    /**
     *
     * @param tableName
     * @param joinType
     * @param rawLeftField
     * @param rawRightField
     */
    public Join(String tableName, JoinType joinType, String rawLeftField, String rawRightField) {
        this(tableName, joinType);
        addCondition(new FieldOperationCondition(rawLeftField, new RawValue(rawRightField)));
    }

    /**
     *
     * @return
     */
    public JoinType getJoinType() {
        return joinType;
    }

    /**
     *
     * @return
     */
    public String getTableName() {
        return tableName;
    }

    /**
     *
     * @return
     */
    public ConditionGroup getConditions() {
        return conditionGroup;
    }

    /**
     *
     * @return
     */
    public ConditionGroupConnector getConnector() {
        return conditionGroup.getConnector();
    }

    /**
     *
     * @param connector
     * @return
     */
    public Join setConnector(ConditionGroupConnector connector) {
        conditionGroup.setConnector(connector);
        return this;
    }

    /**
     *
     * @return
     */
    public Join clearConditions() {
        conditionGroup.clearConditions();
        return this;
    }

    /**
     *
     * @param condition
     * @return
     */
    public Join addCondition(Condition condition) {
        conditionGroup.addCondition(condition);
        return this;
    }
}
