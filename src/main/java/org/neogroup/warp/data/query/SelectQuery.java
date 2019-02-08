
package org.neogroup.warp.data.query;

import org.neogroup.warp.data.query.conditions.ConditionGroup;
import org.neogroup.warp.data.query.fields.Field;
import org.neogroup.warp.data.query.fields.SelectField;
import org.neogroup.warp.data.query.fields.SortField;
import org.neogroup.warp.data.query.joins.Join;
import org.neogroup.warp.data.query.traits.*;

import java.util.ArrayList;
import java.util.List;

public class SelectQuery extends Query implements
        HasTable<SelectQuery>,
        HasTableAlias<SelectQuery>,
        HasDistinct<SelectQuery>,
        HasSelectFields<SelectQuery>,
        HasJoins<SelectQuery>,
        HasWhereConditions<SelectQuery>,
        HasHavingConditions<SelectQuery>,
        HasOrderByFields<SelectQuery>,
        HasGroupByFields<SelectQuery>,
        HasLimit<SelectQuery>,
        HasOffset<SelectQuery> {

    private String tableName;
    private String tableAlias;
    private List<SelectField> selectFields;
    private List<Field> groupByFields;
    private List<SortField> orderByFields;
    private ConditionGroup whereConditionGroup;
    private ConditionGroup havingConditionGroup;
    private List<Join> joins;
    private Boolean distinct;
    private Integer limit;
    private Integer offset;

    public SelectQuery() {
        this.tableName = null;
        this.tableAlias = null;
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

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public SelectQuery setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    @Override
    public String getTableAlias() {
        return tableAlias;
    }

    @Override
    public SelectQuery setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
        return this;
    }

    @Override
    public Boolean isDistinct() {
        return distinct;
    }

    @Override
    public SelectQuery setDistinct(Boolean distinct) {
        this.distinct = distinct;
        return this;
    }

    @Override
    public List<SelectField> getSelectFields() {
        return selectFields;
    }

    @Override
    public SelectQuery setSelectFields(List<SelectField> selectFields) {
        this.selectFields = selectFields;
        return this;
    }

    @Override
    public List<Join> getJoins() {
        return joins;
    }

    @Override
    public SelectQuery setJoins(List<Join> joins) {
        this.joins = joins;
        return this;
    }

    @Override
    public ConditionGroup getWhereConditions() {
        return whereConditionGroup;
    }

    @Override
    public SelectQuery setWhereConditions(ConditionGroup conditionGroup) {
        this.whereConditionGroup = conditionGroup;
        return this;
    }

    @Override
    public ConditionGroup getHavingConditions() {
        return havingConditionGroup;
    }

    @Override
    public SelectQuery setHavingConditions(ConditionGroup conditionGroup) {
        this.havingConditionGroup = conditionGroup;
        return this;
    }

    @Override
    public List<Field> getGroupByFields() {
        return groupByFields;
    }

    @Override
    public SelectQuery setGroupByFields(List<Field> groupByFields) {
        this.groupByFields = groupByFields;
        return this;
    }

    @Override
    public List<SortField> getOrderByFields() {
        return orderByFields;
    }

    @Override
    public SelectQuery setOrderByFields(List<SortField> orderByFields) {
        this.orderByFields = orderByFields;
        return this;
    }

    @Override
    public Integer getLimit() {
        return limit;
    }

    @Override
    public SelectQuery limit(Integer limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public Integer getOffset() {
        return offset;
    }

    @Override
    public SelectQuery offset(Integer offset) {
        this.limit = offset;
        return this;
    }
}