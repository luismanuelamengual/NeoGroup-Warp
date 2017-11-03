package org.neogroup.warp.datasources.query;

public class QueryJoin {

    private final QueryJoinType joinType;
    private final String tableName;
    private final QueryConditionGroup conditionGroup;

    public QueryJoin(String tableName) {
        this(tableName, QueryJoinType.INNER_JOIN);
    }

    public QueryJoin(String tableName, QueryJoinType joinType) {
        this.tableName = tableName;
        this.joinType = joinType;
        this.conditionGroup = new QueryConditionGroup();
    }

    public QueryJoin(String tableName, String leftFieldName, String rightFieldName) {
        this(tableName, QueryJoinType.INNER_JOIN, leftFieldName, rightFieldName);
    }

    public QueryJoin(String tableName, QueryField leftField, QueryField rightField) {
        this(tableName, QueryJoinType.INNER_JOIN, leftField, rightField);
    }

    public QueryJoin(String tableName, QueryJoinType joinType, String leftFieldName, String rightFieldName) {
        this(tableName, joinType);
        conditionGroup.addCondition(new QueryFieldCondition(new QueryField(leftFieldName), new QueryField(rightFieldName)));
    }

    public QueryJoin(String tableName, QueryJoinType joinType, QueryField leftField, QueryField rightField) {
        this(tableName, joinType);
        conditionGroup.addCondition(new QueryFieldCondition(leftField, rightField));
    }

    public QueryJoinType getJoinType() {
        return joinType;
    }

    public String getTableName() {
        return tableName;
    }

    public QueryConditionGroup getConditionGroup() {
        return conditionGroup;
    }
}
