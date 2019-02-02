
package org.neogroup.warp.query.fields;

public class SelectField extends Field {

    private String alias;

    public SelectField (String name) {
        this(name, null);
    }

    public SelectField (String name, String alias) {
        this(null, name, alias);
    }

    public SelectField (String tableName, String name, String alias) {
        super(tableName, name);
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }
}