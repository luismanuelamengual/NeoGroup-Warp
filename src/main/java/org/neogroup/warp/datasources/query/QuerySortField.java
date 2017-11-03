package org.neogroup.warp.datasources.query;

public class QuerySortField {

    private final QueryField field;
    private final QuerySortFieldDirection direction;

    public QuerySortField(String fieldName) {
        this(fieldName, QuerySortFieldDirection.ASC);
    }

    public QuerySortField(QueryField field) {
        this(field, QuerySortFieldDirection.ASC);
    }

    public QuerySortField(String fieldName, QuerySortFieldDirection direction) {
        this(new QueryField(fieldName), direction);
    }

    public QuerySortField(QueryField field, QuerySortFieldDirection direction) {
        this.field = field;
        this.direction = direction;
    }

    public QueryField getField() {
        return field;
    }

    public QuerySortFieldDirection getDirection() {
        return direction;
    }
}
