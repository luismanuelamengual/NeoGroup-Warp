package org.neogroup.warp.datasources.query;

import java.util.ArrayList;
import java.util.List;

public class SelectQuery extends RequestQuery {

    private final String tableName;
    private final List<QueryField> returnFields;
    private final QueryConditionGroup whereConditionGroup;
    private final QueryConditionGroup havingConditionGroup;
    private final List<QuerySortField> orderByFields;
    private final List<QueryField> groupByFields;
    private final List<QueryJoin> joins;
    private Integer limit;
    private Integer offset;

    public SelectQuery(String tableName) {

        this.tableName = tableName;
        returnFields = new ArrayList<>();
        whereConditionGroup = new QueryConditionGroup();
        havingConditionGroup = new QueryConditionGroup();
        joins = new ArrayList<>();
        orderByFields = new ArrayList<>();
        groupByFields = new ArrayList<>();
    }

    public String getTableName() {
        return tableName;
    }

    public SelectQuery clearReturnFields () {
        returnFields.clear();
        return this;
    }

    public List<QueryField> getReturnFields() {
        return returnFields;
    }

    public SelectQuery addReturnField (String fieldName) {
        addReturnField(fieldName, null);
        return this;
    }

    public SelectQuery addReturnField (String fieldName, String fieldLabel) {
        addReturnField(null, fieldName, fieldLabel);
        return this;
    }

    public SelectQuery addReturnField (String fieldTableName, String fieldName, String fieldAlias) {
        addReturnField(new QueryField(fieldTableName, fieldName, fieldAlias));
        return this;
    }

    public SelectQuery addReturnField (QueryField field) {
        returnFields.add(field);
        return this;
    }

    public SelectQuery addReturnFields (String... fieldNames) {
        addReturnFields(fieldNames, null);
        return this;
    }

    public SelectQuery addReturnFields (String[] fieldNames, String fieldsFormat) {
        addReturnFields(null, fieldNames, fieldsFormat);
        return this;
    }

    public SelectQuery addReturnFields (String fieldsTableName, String[] fieldNames, String fieldsFormat) {
        for (String fieldName : fieldNames) {
            String fieldAlias = null;
            if (fieldsFormat != null) {
                fieldAlias = fieldsFormat.replaceAll("%s", fieldName);
            }
            addReturnField(fieldsTableName, fieldName, fieldAlias);
        }
        return this;
    }

    public QueryConditionGroup getWhereConditionGroup() {
        return whereConditionGroup;
    }

    public SelectQuery addWhere (QueryCondition whereCondition) {
        whereConditionGroup.addCondition(whereCondition);
        return this;
    }

    public SelectQuery addWhere (String fieldName, String operator, Object value) {
        whereConditionGroup.addCondition(new QueryFieldCondition(fieldName, operator, value));
        return this;
    }

    public SelectQuery addWhere (QueryField field, String operator, Object value) {
        whereConditionGroup.addCondition(new QueryFieldCondition(field, operator, value));
        return this;
    }

    public QueryConditionGroup getHavingConditionGroup() {
        return havingConditionGroup;
    }

    public SelectQuery addHaving (QueryCondition havingCondition) {
        havingConditionGroup.addCondition(havingCondition);
        return this;
    }

    public SelectQuery addHaving (String fieldName, String operator, Object value) {
        havingConditionGroup.addCondition(new QueryFieldCondition(fieldName, operator, value));
        return this;
    }

    public SelectQuery addHaving (QueryField field, String operator, Object value) {
        havingConditionGroup.addCondition(new QueryFieldCondition(field, operator, value));
        return this;
    }

    public SelectQuery clearOrderByFields () {
        orderByFields.clear();
        return this;
    }

    public List<QuerySortField> getOrderByFields() {
        return orderByFields;
    }

    public SelectQuery addOrderByField (QuerySortField orderByField) {
        orderByFields.add(orderByField);
        return this;
    }

    public SelectQuery addOrderByField (String fieldName) {
        orderByFields.add(new QuerySortField(fieldName));
        return this;
    }

    public SelectQuery addOrderByField (QueryField field) {
        orderByFields.add(new QuerySortField(field));
        return this;
    }

    public SelectQuery addOrderByField (String fieldName, QuerySortFieldDirection direction) {
        orderByFields.add(new QuerySortField(fieldName, direction));
        return this;
    }

    public SelectQuery addOrderByField (QueryField field, QuerySortFieldDirection direction) {
        orderByFields.add(new QuerySortField(field, direction));
        return this;
    }

    public SelectQuery clearGroupByFields () {
        groupByFields.clear();
        return this;
    }

    public List<QueryField> getGroupByFields() {
        return groupByFields;
    }

    public SelectQuery addGroupByField (String fieldName) {
        addGroupByField(new QueryField(fieldName));
        return this;
    }

    public SelectQuery addGroupByField (QueryField field) {
        groupByFields.add(field);
        return this;
    }

    public SelectQuery clearJoins () {
        joins.clear();
        return this;
    }

    public List<QueryJoin> getJoins() {
        return joins;
    }

    public SelectQuery addJoin(QueryJoin join) {
        joins.add(join);
        return this;
    }

    public SelectQuery addJoin(String tableName, String leftFieldName, String rightFieldName) {
        joins.add(new QueryJoin(tableName, leftFieldName, rightFieldName));
        return this;
    }

    public SelectQuery addJoin(String tableName, QueryField leftField, QueryField rightField) {
        joins.add(new QueryJoin(tableName, leftField, rightField));
        return this;
    }

    public SelectQuery addJoin(String tableName, QueryJoinType joinType, String leftFieldName, String rightFieldName) {
        joins.add(new QueryJoin(tableName, joinType, leftFieldName, rightFieldName));
        return this;
    }

    public SelectQuery addJoin(String tableName, QueryJoinType joinType, QueryField leftField, QueryField rightField) {
        joins.add(new QueryJoin(tableName, joinType, leftField, rightField));
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public SelectQuery setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public Integer getOffset() {
        return offset;
    }

    public SelectQuery setOffset(Integer offset) {
        this.offset = offset;
        return this;
    }
}
