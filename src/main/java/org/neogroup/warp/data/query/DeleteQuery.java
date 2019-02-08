
package org.neogroup.warp.data.query;

import org.neogroup.warp.data.query.conditions.ConditionGroup;
import org.neogroup.warp.data.query.traits.HasTable;
import org.neogroup.warp.data.query.traits.HasWhereConditions;

public class DeleteQuery extends Query implements
        HasTable<DeleteQuery>,
        HasWhereConditions<DeleteQuery> {

    private String tableName;
    private ConditionGroup whereConditionGroup;

    public DeleteQuery() {
        this.tableName = null;
        this.whereConditionGroup = new ConditionGroup();
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public DeleteQuery setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    @Override
    public ConditionGroup getWhereConditions() {
        return whereConditionGroup;
    }

    @Override
    public DeleteQuery setWhereConditions(ConditionGroup conditionGroup) {
        this.whereConditionGroup = conditionGroup;
        return this;
    }
}