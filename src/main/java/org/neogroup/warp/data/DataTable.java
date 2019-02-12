package org.neogroup.warp.data;

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

public class DataTable implements
        HasTable<DataTable>,
        HasTableAlias<DataTable>,
        HasSubQuery<DataTable>,
        HasFields<DataTable>,
        HasDistinct<DataTable>,
        HasSelectFields<DataTable>,
        HasJoins<DataTable>,
        HasWhereConditions<DataTable>,
        HasHavingConditions<DataTable>,
        HasOrderByFields<DataTable>,
        HasGroupByFields<DataTable>,
        HasLimit<DataTable>,
        HasOffset<DataTable> {

    private DataConnection connection;
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

    public DataTable(DataConnection connection, String table) {
        this.connection = connection;
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

    public Collection<DataObject> find() {
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
        return connection.query(query);
    }

    public int insert () {
        InsertQuery query = new InsertQuery();
        query.setTableName(tableName);
        query.setFields(fields);
        return connection.execute(query);
    }

    public int update () {
        UpdateQuery query = new UpdateQuery();
        query.setTableName(tableName);
        query.setFields(fields);
        query.setWhereConditions(whereConditionGroup);
        return connection.execute(query);
    }

    public int delete () {
        DeleteQuery query = new DeleteQuery();
        query.setTableName(tableName);
        query.setWhereConditions(whereConditionGroup);
        return connection.execute(query);
    }

    public DataObject first () {
        return (DataObject)limit(1).find().iterator().next();
    }

    @Override
    public Boolean isDistinct() {
        return distinct;
    }

    @Override
    public DataTable setDistinct(Boolean distinct) {
        this.distinct = distinct;
        return this;
    }

    @Override
    public Map<String, Object> getFields() {
        return fields;
    }

    @Override
    public DataTable setFields(Map<String, Object> fields) {
        this.fields = fields;
        return this;
    }

    @Override
    public List<Field> getGroupByFields() {
        return groupByFields;
    }

    @Override
    public DataTable setGroupByFields(List<Field> groupByFields) {
        this.groupByFields = groupByFields;
        return this;
    }

    @Override
    public ConditionGroup getHavingConditions() {
        return havingConditionGroup;
    }

    @Override
    public DataTable setHavingConditions(ConditionGroup conditionGroup) {
        this.havingConditionGroup = conditionGroup;
        return this;
    }

    @Override
    public List<Join> getJoins() {
        return joins;
    }

    @Override
    public DataTable setJoins(List<Join> joins) {
        this.joins = joins;
        return this;
    }

    @Override
    public Integer getLimit() {
        return limit;
    }

    @Override
    public DataTable limit(Integer limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public Integer getOffset() {
        return offset;
    }

    @Override
    public DataTable offset(Integer offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public List<SortField> getOrderByFields() {
        return orderByFields;
    }

    @Override
    public DataTable setOrderByFields(List<SortField> orderByFields) {
        this.orderByFields = orderByFields;
        return this;
    }

    @Override
    public List<SelectField> getSelectFields() {
        return selectFields;
    }

    @Override
    public DataTable setSelectFields(List<SelectField> selectFields) {
        this.selectFields = selectFields;
        return this;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public DataTable setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    @Override
    public String getTableAlias() {
        return tableAlias;
    }

    @Override
    public DataTable setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
        return this;
    }

    @Override
    public ConditionGroup getWhereConditions() {
        return whereConditionGroup;
    }

    @Override
    public DataTable setWhereConditions(ConditionGroup conditionGroup) {
        this.whereConditionGroup = conditionGroup;
        return this;
    }

    @Override
    public SelectQuery getSubQuery() {
        return subQuery;
    }

    @Override
    public DataTable setSubQuery(SelectQuery subQuery) {
        this.subQuery = subQuery;
        return this;
    }
}