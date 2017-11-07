package org.neogroup.warp.datasources.query;

public class QuerySortField extends QueryField {

    private final QuerySortFieldDirection direction;

    public QuerySortField(String fieldExpression) {
        this(null, fieldExpression);
    }

    public QuerySortField(String fieldExpression, QuerySortFieldDirection direction) {
        this(null, fieldExpression, direction);
    }

    public QuerySortField(String fieldTableName, String fieldName) {
        this(fieldTableName, fieldName, QuerySortFieldDirection.ASC);
    }

    public QuerySortField(String fieldTableName, String fieldName, QuerySortFieldDirection direction) {
        super(fieldTableName, fieldName);
        this.direction = direction;
    }
}
