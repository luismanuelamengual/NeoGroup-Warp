package org.neogroup.warp.data.query.conditions;

import org.neogroup.warp.data.query.RawStatement;

import java.util.Map;

public class RawCondition extends Condition {

    private RawStatement statement;

    public RawCondition(String statement) {
        this.statement = new RawStatement(statement);
    }

    public String getStatement() {
        return statement.getStatement();
    }

    public Map<String, Object> getBindings() {
        return statement.getBindings();
    }

    public RawStatement clearBindings() {
        return statement.clearBindings();
    }

    public RawStatement set(String field, Object value) {
        return statement.set(field, value);
    }

    public <V> V get(String field) {
        return statement.get(field);
    }
}