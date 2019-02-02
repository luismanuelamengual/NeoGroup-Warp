package org.neogroup.warp.query.joins;

import org.neogroup.warp.query.conditions.ConditionGroup;

public class Join extends ConditionGroup {

    private String tableName;
    private String tableAlias;
    private JoinType type;

    public Join(String tableName) {
        this(tableName, JoinType.INNER_JOIN);
    }

    public Join(String tableName, JoinType type) {
        this(tableName, null, type);
    }

    public Join(String tableName, String tableAlias) {
        this(tableName, tableAlias, JoinType.INNER_JOIN);
    }

    public Join(String tableName, String tableAlias, JoinType type) {
        this.tableName = tableName;
        this.tableAlias = tableAlias;
        this.type = type;
    }

    public String getTableName() {
        return tableName;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    public JoinType getType() {
        return type;
    }
}