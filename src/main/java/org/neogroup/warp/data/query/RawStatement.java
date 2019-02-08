package org.neogroup.warp.data.query;

import java.util.ArrayList;
import java.util.List;

public class RawStatement {

    private String statement;
    private List<Object> bindings;

    public RawStatement(String statement) {
        this.statement = statement;
        this.bindings = new ArrayList<>();
    }

    public String getStatement() {
        return statement;
    }

    public List<Object> getBindings() {
        return bindings;
    }

    public RawStatement clearBindings () {
        bindings.clear();
        return this;
    }

    public RawStatement addBinding(Object value) {
        bindings.add(value);
        return this;
    }
}