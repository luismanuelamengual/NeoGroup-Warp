package org.neogroup.warp.datasources.query;

public class QueryReturnField extends QueryField {

    private final String fieldAlias;

    public QueryReturnField(String fieldExpression) {
        this(fieldExpression, null);
    }

    public QueryReturnField(String fieldExpression, String fieldAlias) {
        super(fieldExpression);
        this.fieldAlias = fieldAlias;
    }

    public QueryReturnField(String fieldTableName, String fieldName, String fieldAlias) {
        super(fieldTableName, fieldName);
        this.fieldAlias = fieldAlias;
    }

    public String getFieldAlias() {
        return fieldAlias;
    }
}
