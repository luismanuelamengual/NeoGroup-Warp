package org.neogroup.warp.datasources.query;

public class QueryField {

    private String fieldTableName;
    private String fieldName;

    public QueryField(String fieldExpression) {
        this(null, fieldExpression);
    }

    public QueryField(String fieldTableName, String fieldName) {
        this.fieldTableName = fieldTableName;
        this.fieldName = fieldName;
    }

    public void setFieldTableName(String fieldTableName) {
        this.fieldTableName = fieldTableName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldTableName() {
        return fieldTableName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
