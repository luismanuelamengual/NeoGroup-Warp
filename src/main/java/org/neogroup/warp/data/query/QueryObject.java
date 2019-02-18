package org.neogroup.warp.data.query;

import org.neogroup.warp.data.query.conditions.ConditionGroup;
import org.neogroup.warp.data.query.fields.Field;
import org.neogroup.warp.data.query.fields.SelectField;
import org.neogroup.warp.data.query.fields.SortField;
import org.neogroup.warp.data.query.joins.Join;
import org.neogroup.warp.data.query.traits.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QueryObject<R extends QueryObject> implements
        HasTable<R>,
        HasTableAlias<R>,
        HasSubQuery<R>,
        HasFields<R>,
        HasDistinct<R>,
        HasSelectFields<R>,
        HasJoins<R>,
        HasWhereConditions<R>,
        HasHavingConditions<R>,
        HasOrderByFields<R>,
        HasGroupByFields<R>,
        HasLimit<R>,
        HasOffset<R> {

    protected String tableName;
    protected String tableAlias;
    protected SelectQuery subQuery;
    protected Map<String, Object> fields;
    protected List<SelectField> selectFields;
    protected List<Field> groupByFields;
    protected List<SortField> orderByFields;
    protected ConditionGroup whereConditionGroup;
    protected ConditionGroup havingConditionGroup;
    protected List<Join> joins;
    protected Boolean distinct;
    protected Integer limit;
    protected Integer offset;

    public QueryObject() {
        this(null);
    }

    public QueryObject(String table) {
        this.tableName = table;
        this.tableAlias = null;
        this.subQuery = null;
        this.fields = new LinkedHashMap<>();
        this.selectFields = new ArrayList<>();
        this.groupByFields = new ArrayList<>();
        this.orderByFields = new ArrayList<>();
        this.whereConditionGroup = new ConditionGroup();
        this.havingConditionGroup = new ConditionGroup();
        this.joins = new ArrayList<>();
        this.limit = null;
        this.offset = null;
        this.distinct = false;
    }

    protected SelectQuery createSelectQuery() {
        SelectQuery query = new SelectQuery();
        query.setTableName(tableName);
        query.setTableAlias(tableAlias);
        query.setSelectFields(selectFields);
        query.setGroupByFields(groupByFields);
        query.setOrderByFields(orderByFields);
        query.setWhereConditions(whereConditionGroup);
        query.setHavingConditions(havingConditionGroup);
        query.setJoins(joins);
        query.limit(limit);
        query.offset(offset);
        query.setDistinct(distinct);
        return query;
    }

    protected InsertQuery createInsertQuery () {
        InsertQuery query = new InsertQuery();
        query.setTableName(tableName);
        query.setFields(fields);
        return query;
    }

    protected UpdateQuery createUpdateQuery () {
        UpdateQuery query = new UpdateQuery();
        query.setTableName(tableName);
        query.setFields(fields);
        query.setWhereConditions(whereConditionGroup);
        return query;
    }

    protected DeleteQuery createDeleteQuery () {
        DeleteQuery query = new DeleteQuery();
        query.setTableName(tableName);
        query.setWhereConditions(whereConditionGroup);
        return query;
    }

    @Override
    public Boolean isDistinct() {
        return distinct;
    }

    @Override
    public R setDistinct(Boolean distinct) {
        this.distinct = distinct;
        return (R)this;
    }

    @Override
    public Map<String, Object> getFields() {
        return fields;
    }

    @Override
    public R setFields(Map<String, Object> fields) {
        this.fields = fields;
        return (R)this;
    }

    @Override
    public List<Field> getGroupByFields() {
        return groupByFields;
    }

    @Override
    public R setGroupByFields(List<Field> groupByFields) {
        this.groupByFields = groupByFields;
        return (R)this;
    }

    @Override
    public ConditionGroup getHavingConditions() {
        return havingConditionGroup;
    }

    @Override
    public R setHavingConditions(ConditionGroup conditionGroup) {
        this.havingConditionGroup = conditionGroup;
        return (R)this;
    }

    @Override
    public List<Join> getJoins() {
        return joins;
    }

    @Override
    public R setJoins(List<Join> joins) {
        this.joins = joins;
        return (R)this;
    }

    @Override
    public Integer getLimit() {
        return limit;
    }

    @Override
    public R limit(Integer limit) {
        this.limit = limit;
        return (R)this;
    }

    @Override
    public Integer getOffset() {
        return offset;
    }

    @Override
    public R offset(Integer offset) {
        this.offset = offset;
        return (R)this;
    }

    @Override
    public List<SortField> getOrderByFields() {
        return orderByFields;
    }

    @Override
    public R setOrderByFields(List<SortField> orderByFields) {
        this.orderByFields = orderByFields;
        return (R)this;
    }

    @Override
    public List<SelectField> getSelectFields() {
        return selectFields;
    }

    @Override
    public R setSelectFields(List<SelectField> selectFields) {
        this.selectFields = selectFields;
        return (R)this;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public R setTableName(String tableName) {
        this.tableName = tableName;
        return (R)this;
    }

    @Override
    public String getTableAlias() {
        return tableAlias;
    }

    @Override
    public R setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
        return (R)this;
    }

    @Override
    public ConditionGroup getWhereConditions() {
        return whereConditionGroup;
    }

    @Override
    public R setWhereConditions(ConditionGroup conditionGroup) {
        this.whereConditionGroup = conditionGroup;
        return (R)this;
    }

    @Override
    public SelectQuery getSubQuery() {
        return subQuery;
    }

    @Override
    public R setSubQuery(SelectQuery subQuery) {
        this.subQuery = subQuery;
        return (R)this;
    }
}