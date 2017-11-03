package org.neogroup.warp.datasources.query;

import java.util.ArrayList;
import java.util.List;

public class Select extends Query {

    private final List<QueryField> returnFields;
    private final QueryConditionGroup whereConditionGroup;
    private final QueryConditionGroup havingConditionGroup;
    private final List<QuerySortField> orderByFields;
    private final List<QueryField> groupByFields;
    private final List<QueryJoin> joins;
    private Integer limit;
    private Integer offset;

    public Select(String tableName) {
        super(tableName);
        returnFields = new ArrayList<>();
        whereConditionGroup = new QueryConditionGroup();
        havingConditionGroup = new QueryConditionGroup();
        joins = new ArrayList<>();
        orderByFields = new ArrayList<>();
        groupByFields = new ArrayList<>();
    }

    public void clearReturnFields () {
        returnFields.clear();
    }

    public List<QueryField> getReturnFields() {
        return returnFields;
    }

    public void addReturnField (String fieldName) {
        addReturnField(fieldName, null);
    }

    public void addReturnField (String fieldName, String fieldLabel) {
        addReturnField(null, fieldName, fieldLabel);
    }

    public void addReturnField (String fieldTableName, String fieldName, String fieldAlias) {
        addReturnField(new QueryField(fieldTableName, fieldName, fieldAlias));
    }

    public void addReturnField (QueryField field) {
        returnFields.add(field);
    }

    public void addReturnFields (String... fieldNames) {
        addReturnFields(fieldNames, null);
    }

    public void addReturnFields (String[] fieldNames, String fieldsFormat) {
        addReturnFields(null, fieldNames, fieldsFormat);
    }

    public void addReturnFields (String fieldsTableName, String[] fieldNames, String fieldsFormat) {
        for (String fieldName : fieldNames) {
            String fieldAlias = null;
            if (fieldsFormat != null) {
                fieldAlias = fieldsFormat.replaceAll("%s", fieldName);
            }
            addReturnField(fieldsTableName, fieldName, fieldAlias);
        }
    }

    public QueryConditionGroup getWhereConditionGroup() {
        return whereConditionGroup;
    }

    public void addWhere (QueryCondition whereCondition) {
        whereConditionGroup.addCondition(whereCondition);
    }

    public void addWhere (String fieldName, String operator, Object value) {
        whereConditionGroup.addCondition(new QueryFieldCondition(fieldName, operator, value));
    }

    public void addWhere (QueryField field, String operator, Object value) {
        whereConditionGroup.addCondition(new QueryFieldCondition(field, operator, value));
    }

    public QueryConditionGroup getHavingConditionGroup() {
        return havingConditionGroup;
    }

    public void addHaving (QueryCondition havingCondition) {
        havingConditionGroup.addCondition(havingCondition);
    }

    public void addHaving (String fieldName, String operator, Object value) {
        havingConditionGroup.addCondition(new QueryFieldCondition(fieldName, operator, value));
    }

    public void addHaving (QueryField field, String operator, Object value) {
        havingConditionGroup.addCondition(new QueryFieldCondition(field, operator, value));
    }

    public void clearOrderByFields () {
        orderByFields.clear();
    }

    public List<QuerySortField> getOrderByFields() {
        return orderByFields;
    }

    public void addOrderByField (QuerySortField orderByField) {
        orderByFields.add(orderByField);
    }

    public void addOrderByField (String fieldName) {
        orderByFields.add(new QuerySortField(fieldName));
    }

    public void addOrderByField (QueryField field) {
        orderByFields.add(new QuerySortField(field));
    }

    public void addOrderByField (String fieldName, QuerySortFieldDirection direction) {
        orderByFields.add(new QuerySortField(fieldName, direction));
    }

    public void addOrderByField (QueryField field, QuerySortFieldDirection direction) {
        orderByFields.add(new QuerySortField(field, direction));
    }

    public void clearGroupByFields () {
        groupByFields.clear();
    }

    public List<QueryField> getGroupByFields() {
        return groupByFields;
    }

    public void addGroupByField (String fieldName) {
        addGroupByField(new QueryField(fieldName));
    }

    public void addGroupByField (QueryField field) {
        groupByFields.add(field);
    }

    public void clearJoins () {
        joins.clear();
    }

    public List<QueryJoin> getJoins() {
        return joins;
    }

    public void addJoin(QueryJoin join) {
        joins.add(join);
    }

    public void addJoin(String tableName, String leftFieldName, String rightFieldName) {
        joins.add(new QueryJoin(tableName, leftFieldName, rightFieldName));
    }

    public void addJoin(String tableName, QueryField leftField, QueryField rightField) {
        joins.add(new QueryJoin(tableName, leftField, rightField));
    }

    public void addJoin(String tableName, QueryJoinType joinType, String leftFieldName, String rightFieldName) {
        joins.add(new QueryJoin(tableName, joinType, leftFieldName, rightFieldName));
    }

    public void addJoin(String tableName, QueryJoinType joinType, QueryField leftField, QueryField rightField) {
        joins.add(new QueryJoin(tableName, joinType, leftField, rightField));
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
