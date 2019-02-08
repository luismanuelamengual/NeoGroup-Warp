package org.neogroup.warp.data.query.conditions;

import org.neogroup.warp.data.query.RawStatement;

import java.util.List;

public class RawCondition extends Condition {

    private RawStatement statement;

    public RawCondition(String statement) {
        this.statement = new RawStatement(statement);
    }

    public String getStatement() {
        return statement.getStatement();
    }

    public List<Object> getBindings() {
        return statement.getBindings();
    }

    public RawStatement clearBindings() {
        return statement.clearBindings();
    }

    public RawStatement addBinding(Object value) {
        return statement.addBinding(value);
    }
}