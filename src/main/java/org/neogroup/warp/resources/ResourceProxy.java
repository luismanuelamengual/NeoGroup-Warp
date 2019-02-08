package org.neogroup.warp.resources;

import org.neogroup.warp.data.query.DeleteQuery;
import org.neogroup.warp.data.query.InsertQuery;
import org.neogroup.warp.data.query.SelectQuery;
import org.neogroup.warp.data.query.UpdateQuery;
import org.neogroup.warp.data.query.conditions.ConditionGroup;
import org.neogroup.warp.data.query.fields.Field;
import org.neogroup.warp.data.query.fields.SelectField;
import org.neogroup.warp.data.query.fields.SortField;
import org.neogroup.warp.data.query.joins.Join;
import org.neogroup.warp.data.query.traits.*;

import java.util.*;

public class ResourceProxy<T> implements
    HasTable<ResourceProxy<T>>,
    HasTableAlias<ResourceProxy<T>>,
    HasFields<ResourceProxy<T>>,
    HasDistinct<ResourceProxy<T>>,
    HasSelectFields<ResourceProxy<T>>,
    HasJoins<ResourceProxy<T>>,
    HasWhereConditions<ResourceProxy<T>>,
    HasHavingConditions<ResourceProxy<T>>,
    HasOrderByFields<ResourceProxy<T>>,
    HasGroupByFields<ResourceProxy<T>>,
    HasLimit<ResourceProxy<T>>,
    HasOffset<ResourceProxy<T>> {

    private Resource<T> resource;
    private String tableName;
    private String tableAlias;
    private Map<String, Object> fields;
    private List<SelectField> selectFields;
    private List<Field> groupByFields;
    private List<SortField> orderByFields;
    private ConditionGroup whereConditionGroup;
    private ConditionGroup havingConditionGroup;
    private List<Join> joins;
    private Boolean distinct;
    private Integer limit;
    private Integer offset;

    public ResourceProxy(String resourceName, Resource<T> resource) {
        this.resource = resource;
        this.tableName = resourceName;
        this.tableAlias = null;
        this.fields = new HashMap<>();
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

    public Collection<T> find() {
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
        return resource.find(query);
    }

    public T insert () {
        InsertQuery query = new InsertQuery();
        query.setTableName(tableName);
        query.setFields(fields);
        return resource.insert(query);
    }

    public T update () {
        UpdateQuery query = new UpdateQuery();
        query.setTableName(tableName);
        query.setFields(fields);
        query.setWhereConditions(whereConditionGroup);
        return resource.update(query);
    }

    public T delete () {
        DeleteQuery query = new DeleteQuery();
        query.setTableName(tableName);
        query.setWhereConditions(whereConditionGroup);
        return resource.delete(query);
    }

    public T first () {
        return limit(1).find().iterator().next();
    }

    @Override
    public Boolean isDistinct() {
        return distinct;
    }

    @Override
    public ResourceProxy<T> setDistinct(Boolean distinct) {
        this.distinct = distinct;
        return this;
    }

    @Override
    public Map<String, Object> getFields() {
        return fields;
    }

    @Override
    public ResourceProxy<T> setFields(Map<String, Object> fields) {
        this.fields = fields;
        return this;
    }

    @Override
    public List<Field> getGroupByFields() {
        return groupByFields;
    }

    @Override
    public ResourceProxy<T> setGroupByFields(List<Field> groupByFields) {
        this.groupByFields = groupByFields;
        return this;
    }

    @Override
    public ConditionGroup getHavingConditions() {
        return havingConditionGroup;
    }

    @Override
    public ResourceProxy<T> setHavingConditions(ConditionGroup conditionGroup) {
        this.havingConditionGroup = conditionGroup;
        return this;
    }

    @Override
    public List<Join> getJoins() {
        return joins;
    }

    @Override
    public ResourceProxy<T> setJoins(List<Join> joins) {
        this.joins = joins;
        return this;
    }

    @Override
    public Integer getLimit() {
        return limit;
    }

    @Override
    public ResourceProxy<T> limit(Integer limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public Integer getOffset() {
        return offset;
    }

    @Override
    public ResourceProxy<T> offset(Integer offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public List<SortField> getOrderByFields() {
        return orderByFields;
    }

    @Override
    public ResourceProxy<T> setOrderByFields(List<SortField> orderByFields) {
        this.orderByFields = orderByFields;
        return this;
    }

    @Override
    public List<SelectField> getSelectFields() {
        return selectFields;
    }

    @Override
    public ResourceProxy<T> setSelectFields(List<SelectField> selectFields) {
        this.selectFields = selectFields;
        return this;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public ResourceProxy<T> setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    @Override
    public String getTableAlias() {
        return tableAlias;
    }

    @Override
    public ResourceProxy<T> setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
        return this;
    }

    @Override
    public ConditionGroup getWhereConditions() {
        return whereConditionGroup;
    }

    @Override
    public ResourceProxy<T> setWhereConditions(ConditionGroup conditionGroup) {
        this.whereConditionGroup = conditionGroup;
        return this;
    }
}