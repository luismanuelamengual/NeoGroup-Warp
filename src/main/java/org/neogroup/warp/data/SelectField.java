package org.neogroup.warp.data;

public class SelectField {

    private final String field;
    private final String alias;

    public SelectField(String field) {
        this(field, null);
    }

    public SelectField(String field, String alias) {
        this.field = field;
        this.alias = alias;
    }

    public String getField() {
        return field;
    }

    public String getAlias() {
        return alias;
    }
}
