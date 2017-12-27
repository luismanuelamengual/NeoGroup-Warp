package org.neogroup.warp.data.query.fields;

public class ColumnField extends Field {

    private final String columnName;
    private final String tableName;

    public ColumnField(String columnName) {
        this(columnName, null);
    }

    public ColumnField(String columnName, String tableName) {
        this.columnName = columnName;
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getTableName() {
        return tableName;
    }
}
