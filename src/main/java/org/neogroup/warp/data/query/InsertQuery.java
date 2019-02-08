
package org.neogroup.warp.data.query;

import org.neogroup.warp.data.query.traits.HasFields;
import org.neogroup.warp.data.query.traits.HasTable;

import java.util.HashMap;
import java.util.Map;

public class InsertQuery extends Query implements
        HasTable<InsertQuery>,
        HasFields<InsertQuery> {

    private String tableName;
    private Map<String, Object> fields;

    public InsertQuery() {
        this.tableName = null;
        this.fields = new HashMap<>();
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public InsertQuery setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    @Override
    public Map<String, Object> getFields() {
        return fields;
    }

    @Override
    public InsertQuery setFields(Map<String, Object> fields) {
        this.fields = fields;
        return this;
    }
}