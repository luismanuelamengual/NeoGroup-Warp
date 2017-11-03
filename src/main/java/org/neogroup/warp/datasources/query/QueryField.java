package org.neogroup.warp.datasources.query;

public class QueryField {

    private String fieldTableName;
    private String fieldName;
    private String fieldAlias;

    public QueryField(String fieldName) {
        this(null, fieldName, null);
    }

    public QueryField(String fieldName, String fieldAlias) {
        this(null, fieldName, fieldAlias);
    }

    public QueryField(String fieldTableName, String fieldName, String fieldAlias) {
        this.fieldTableName = fieldTableName;
        this.fieldName = fieldName;
        this.fieldAlias = fieldAlias;
    }

    public void setFieldTableName(String fieldTableName) {
        this.fieldTableName = fieldTableName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setFieldAlias(String fieldAlias) {
        this.fieldAlias = fieldAlias;
    }

    public String getFieldTableName() {
        return fieldTableName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldAlias() {
        return fieldAlias;
    }
}
