
package org.neogroup.warp.data.query;

import org.neogroup.warp.data.query.conditions.ConditionGroup;
import org.neogroup.warp.data.query.traits.HasFields;
import org.neogroup.warp.data.query.traits.HasTable;
import org.neogroup.warp.data.query.traits.HasWhereConditions;

import java.util.HashMap;
import java.util.Map;

public class UpdateQuery extends Query implements
        HasTable<UpdateQuery>,
        HasFields<UpdateQuery>,
        HasWhereConditions<UpdateQuery> {

    private String tableName;
    private Map<String, Object> fields;
    private ConditionGroup whereConditionGroup;

    public UpdateQuery() {
        this.tableName = null;
        this.fields = new HashMap<>();
        this.whereConditionGroup = new ConditionGroup();
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public UpdateQuery setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    @Override
    public Map<String, Object> getFields() {
        return fields;
    }

    @Override
    public UpdateQuery setFields(Map<String, Object> fields) {
        this.fields = fields;
        return this;
    }

    @Override
    public ConditionGroup getWhereConditions() {
        return whereConditionGroup;
    }

    @Override
    public UpdateQuery setWhereConditions(ConditionGroup conditionGroup) {
        this.whereConditionGroup = conditionGroup;
        return this;
    }
}