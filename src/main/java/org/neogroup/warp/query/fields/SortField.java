package org.neogroup.warp.query.fields;

public class SortField extends Field {

    private SortDirection direction;

    public SortField(String name) {
        this(name, SortDirection.ASC);
    }

    public SortField(String tableName, String name) {
        this(tableName, name, SortDirection.ASC);
    }

    public SortField(String name, SortDirection direction) {
        super(name);
        this.direction = direction;
    }

    public SortField(String tableName, String name, SortDirection direction) {
        super(tableName, name);
        this.direction = direction;
    }
}