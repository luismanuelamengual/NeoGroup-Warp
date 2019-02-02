package org.neogroup.warp.query.fields;

public class Field {
    private String tableName;
    private String name;

    public Field(String name) {
        this(null, name);
    }

    public Field(String tableName, String name) {
        this.tableName = tableName;
        this.name = name;
    }

    public String getTableName() {
        return tableName;
    }

    public String getName() {
        return name;
    }
}