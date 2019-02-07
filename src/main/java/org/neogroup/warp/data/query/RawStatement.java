package org.neogroup.warp.data.query;

import java.util.HashMap;
import java.util.Map;

public class RawStatement {

    private String statement;
    private Map<String,Object> bindings;

    public RawStatement(String statement) {
        this.statement = statement;
        this.bindings = new HashMap<>();
    }

    public String getStatement() {
        return statement;
    }

    public Map<String, Object> getBindings() {
        return bindings;
    }

    public RawStatement clearBindings () {
        bindings.clear();
        return this;
    }

    public RawStatement set(String field, Object value) {
        bindings.put(field, value);
        return this;
    }

    public <V extends Object> V get(String field) {
        return (V)bindings.get(field);
    }
}