package org.neogroup.warp.datasources.query;

public class QueryFieldCondition extends QueryCondition {

    private final QueryField field;
    private final String operator;
    private final Object value;

    public QueryFieldCondition(String fieldName, Object value) {
        this(new QueryField(fieldName), QueryFieldConditionOperator.EQUALS, value);
    }

    public QueryFieldCondition(QueryField field, Object value) {
        this(field, QueryFieldConditionOperator.EQUALS, value);
    }

    public QueryFieldCondition(String fieldName, String operator, Object value) {
        this(new QueryField(fieldName), operator, value);
    }

    public QueryFieldCondition(QueryField field, String operator, Object value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    public QueryField getField() {
        return field;
    }

    public String getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }
}
