package org.neogroup.warp.resources;

import org.neogroup.warp.data.DataCollection;
import org.neogroup.warp.data.DataObject;
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

public class ResourceProxy implements
    HasTable<ResourceProxy>,
    HasTableAlias<ResourceProxy>,
    HasSubQuery<ResourceProxy>,
    HasFields<ResourceProxy>,
    HasDistinct<ResourceProxy>,
    HasSelectFields<ResourceProxy>,
    HasJoins<ResourceProxy>,
    HasWhereConditions<ResourceProxy>,
    HasHavingConditions<ResourceProxy>,
    HasOrderByFields<ResourceProxy>,
    HasGroupByFields<ResourceProxy>,
    HasLimit<ResourceProxy>,
    HasOffset<ResourceProxy> {

    private Resource resource;
    private String tableName;
    private String tableAlias;
    private SelectQuery subQuery;
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

    public ResourceProxy(String resourceName, Resource resource) {
        this.resource = resource;
        this.tableName = resourceName;
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

    public DataCollection find() {
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

    public DataCollection insert () {
        InsertQuery query = new InsertQuery();
        query.setTableName(tableName);
        query.setFields(fields);
        return resource.insert(query);
    }

    public DataCollection update () {
        UpdateQuery query = new UpdateQuery();
        query.setTableName(tableName);
        query.setFields(fields);
        query.setWhereConditions(whereConditionGroup);
        return resource.update(query);
    }

    public DataCollection delete () {
        DeleteQuery query = new DeleteQuery();
        query.setTableName(tableName);
        query.setWhereConditions(whereConditionGroup);
        return resource.delete(query);
    }

    public DataObject first () {
        return (DataObject)limit(1).find().iterator().next();
    }

    @Override
    public Boolean isDistinct() {
        return distinct;
    }

    @Override
    public ResourceProxy setDistinct(Boolean distinct) {
        this.distinct = distinct;
        return this;
    }

    @Override
    public Map<String, Object> getFields() {
        return fields;
    }

    @Override
    public ResourceProxy setFields(Map<String, Object> fields) {
        this.fields = fields;
        return this;
    }

    @Override
    public List<Field> getGroupByFields() {
        return groupByFields;
    }

    @Override
    public ResourceProxy setGroupByFields(List<Field> groupByFields) {
        this.groupByFields = groupByFields;
        return this;
    }

    @Override
    public ConditionGroup getHavingConditions() {
        return havingConditionGroup;
    }

    @Override
    public ResourceProxy setHavingConditions(ConditionGroup conditionGroup) {
        this.havingConditionGroup = conditionGroup;
        return this;
    }

    @Override
    public List<Join> getJoins() {
        return joins;
    }

    @Override
    public ResourceProxy setJoins(List<Join> joins) {
        this.joins = joins;
        return this;
    }

    @Override
    public Integer getLimit() {
        return limit;
    }

    @Override
    public ResourceProxy limit(Integer limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public Integer getOffset() {
        return offset;
    }

    @Override
    public ResourceProxy offset(Integer offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public List<SortField> getOrderByFields() {
        return orderByFields;
    }

    @Override
    public ResourceProxy setOrderByFields(List<SortField> orderByFields) {
        this.orderByFields = orderByFields;
        return this;
    }

    @Override
    public List<SelectField> getSelectFields() {
        return selectFields;
    }

    @Override
    public ResourceProxy setSelectFields(List<SelectField> selectFields) {
        this.selectFields = selectFields;
        return this;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public ResourceProxy setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    @Override
    public String getTableAlias() {
        return tableAlias;
    }

    @Override
    public ResourceProxy setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
        return this;
    }

    @Override
    public ConditionGroup getWhereConditions() {
        return whereConditionGroup;
    }

    @Override
    public ResourceProxy setWhereConditions(ConditionGroup conditionGroup) {
        this.whereConditionGroup = conditionGroup;
        return this;
    }

    @Override
    public SelectQuery getSubQuery() {
        return subQuery;
    }

    @Override
    public ResourceProxy setSubQuery(SelectQuery subQuery) {
        this.subQuery = subQuery;
        return this;
    }
}