package org.neogroup.warp.resources;

import org.neogroup.warp.data.query.Query;
import org.neogroup.warp.data.query.conditions.*;
import org.neogroup.warp.data.query.fields.Field;
import org.neogroup.warp.data.query.fields.SelectField;
import org.neogroup.warp.data.query.fields.SortDirection;
import org.neogroup.warp.data.query.fields.SortField;
import org.neogroup.warp.data.query.joins.Join;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ResourceProxy<T> {

    private Query query;
    private Resource<T> resource;

    public ResourceProxy(String resourceName, Resource<T> resource) {
        this.query = new Query(resourceName);
        this.resource = resource;
    }

    public Collection<T> find() {
        return resource.find(query);
    }

    public T insert () {
        return resource.insert(query);
    }

    public T update () {
        return resource.update(query);
    }

    public T delete () {
        return resource.delete(query);
    }

    public T first () {
        return limit(1).find().iterator().next();
    }

    public ResourceProxy<T> select(String... fields) {
        query.select(fields);
        return this;
    }

    public String getTableName() {
        return query.getTableName();
    }

    public String getTableAlias() {
        return query.getTableAlias();
    }

    public boolean isDistinct() {
        return query.isDistinct();
    }

    public ResourceProxy<T> setDistinct(boolean distinct) {
        query.setDistinct(distinct);
        return this;
    }

    public Integer getLimit() {
        return query.getLimit();
    }

    public ResourceProxy<T> limit(Integer limit) {
        query.limit(limit);
        return this;
    }

    public Integer getOffset() {
        return query.getOffset();
    }

    public ResourceProxy<T> offset(Integer offset) {
        query.offset(offset);
        return this;
    }

    public ResourceProxy<T> select(SelectField... fields) {
        query.select(fields);
        return this;
    }

    public ResourceProxy<T> selectField(SelectField field) {
        query.selectField(field);
        return this;
    }

    public ResourceProxy<T> selectField(String name) {
        query.selectField(name);
        return this;
    }

    public ResourceProxy<T> selectField(String name, String alias) {
        query.selectField(name, alias);
        return this;
    }

    public ResourceProxy<T> selectField(String tableName, String name, String alias) {
        query.selectField(tableName, name, alias);
        return this;
    }

    public List<SelectField> getSelectFields() {
        return query.getSelectFields();
    }

    public ResourceProxy<T> clearSelectFields() {
        query.clearSelectFields();
        return this;
    }

    public ResourceProxy<T> groupBy(String... groupByFields) {
        query.groupBy(groupByFields);
        return this;
    }

    public ResourceProxy<T> groupBy(Field... groupByFields) {
        query.groupBy(groupByFields);
        return this;
    }

    public List<Field> getGroupByFields() {
        return query.getGroupByFields();
    }

    public ResourceProxy<T> clearGroupByFields() {
        query.clearGroupByFields();
        return this;
    }

    public ResourceProxy<T> orderBy(String... orderByFields) {
        query.orderBy(orderByFields);
        return this;
    }

    public ResourceProxy<T> orderBy(SortField... orderByFields) {
        query.orderBy(orderByFields);
        return this;
    }

    public ResourceProxy<T> orderBy(String field, SortDirection direction) {
        query.orderBy(field, direction);
        return this;
    }

    public List<SortField> getOrderByFields() {
        return query.getOrderByFields();
    }

    public ResourceProxy<T> clearOrderByFields() {
        query.clearOrderByFields();
        return this;
    }

    public ResourceProxy<T> set(String field, Object value) {
        query.set(field, value);
        return this;
    }

    public <V> V get(String field) {
        return query.get(field);
    }

    public Map<String, Object> getFields() {
        return query.getFields();
    }

    public ResourceProxy<T> clearFields() {
        query.clearFields();
        return this;
    }

    public ConditionGroupConnector getWhereConnector() {
        return query.getWhereConnector();
    }

    public ResourceProxy<T> setWhereConnector(ConditionGroupConnector connector) {
        query.setWhereConnector(connector);
        return this;
    }

    public List<Condition> getWhereConditions() {
        return query.getWhereConditions();
    }

    public ResourceProxy<T> clearWhereConditions() {
        query.clearWhereConditions();
        return this;
    }

    public boolean hasWhereConditions() {
        return query.hasWhereConditions();
    }

    public ResourceProxy<T> where(Condition condition) {
        query.where(condition);
        return this;
    }

    public ConditionGroup getWhereConditionGroup() {
        return query.getWhereConditionGroup();
    }

    public ResourceProxy<T> where(String field, Object value) {
        query.where(field, value);
        return this;
    }

    public ResourceProxy<T> where(Field field, Object value) {
        query.where(field, value);
        return this;
    }

    public ResourceProxy<T> where(String field, ConditionOperator operator, Object value) {
        query.where(field, operator, value);
        return this;
    }

    public ResourceProxy<T> where(Field field, ConditionOperator operator, Object value) {
        query.where(field, operator, value);
        return this;
    }

    public ResourceProxy<T> whereGroup(ConditionGroup condition) {
        query.whereGroup(condition);
        return this;
    }

    public ResourceProxy<T> whereRaw(RawCondition condition) {
        query.whereRaw(condition);
        return this;
    }

    public ResourceProxy<T> whereField(String field1, String field2) {
        query.whereField(field1, field2);
        return this;
    }

    public ResourceProxy<T> whereField(Field field1, Field field2) {
        query.whereField(field1, field2);
        return this;
    }

    public ResourceProxy<T> whereField(String field1, ConditionOperator operator, String field2) {
        query.whereField(field1, operator, field2);
        return this;
    }

    public ResourceProxy<T> whereField(Field field1, ConditionOperator operator, Field field2) {
        query.whereField(field1, operator, field2);
        return this;
    }

    public ResourceProxy<T> whereNull(String field) {
        query.whereNull(field);
        return this;
    }

    public ResourceProxy<T> whereNull(Field field) {
        query.whereNull(field);
        return this;
    }

    public ResourceProxy<T> whereNotNull(String field) {
        query.whereNotNull(field);
        return this;
    }

    public ResourceProxy<T> whereNotNull(Field field) {
        query.whereNotNull(field);
        return this;
    }

    public ResourceProxy<T> whereIn(String field, Collection<Object> values) {
        query.whereIn(field, values);
        return this;
    }

    public ResourceProxy<T> whereIn(Field field, Collection<Object> values) {
        query.whereIn(field, values);
        return this;
    }

    public ResourceProxy<T> whereNotIn(String field, Collection<Object> values) {
        query.whereNotIn(field, values);
        return this;
    }

    public ResourceProxy<T> whereNotIn(Field field, Collection<Object> values) {
        query.whereNotIn(field, values);
        return this;
    }

    public ResourceProxy<T> whereContains(String field, Object value) {
        query.whereContains(field, value);
        return this;
    }

    public ResourceProxy<T> whereContains(Field field, Object value) {
        query.whereContains(field, value);
        return this;
    }

    public ResourceProxy<T> whereNotContains(String field, Object value) {
        query.whereNotContains(field, value);
        return this;
    }

    public ResourceProxy<T> whereNotContains(Field field, Object value) {
        query.whereNotContains(field, value);
        return this;
    }

    public ResourceProxy<T> whereGreaterThan(String field, Object value) {
        query.whereGreaterThan(field, value);
        return this;
    }

    public ResourceProxy<T> whereGreaterThan(Field field, Object value) {
        query.whereGreaterThan(field, value);
        return this;
    }

    public ResourceProxy<T> whereGreaterOrEqualsThan(String field, Object value) {
        query.whereGreaterOrEqualsThan(field, value);
        return this;
    }

    public ResourceProxy<T> whereGreaterOrEqualsThan(Field field, Object value) {
        query.whereGreaterOrEqualsThan(field, value);
        return this;
    }

    public ResourceProxy<T> whereLowerThan(String field, Object value) {
        query.whereLowerThan(field, value);
        return this;
    }

    public ResourceProxy<T> whereLowerThan(Field field, Object value) {
        query.whereLowerThan(field, value);
        return this;
    }

    public ResourceProxy<T> whereLowerOrEqualsThan(String field, Object value) {
        query.whereLowerOrEqualsThan(field, value);
        return this;
    }

    public ResourceProxy<T> whereLowerOrEqualsThan(Field field, Object value) {
        query.whereLowerOrEqualsThan(field, value);
        return this;
    }

    public ConditionGroupConnector getHavingConnector() {
        return query.getHavingConnector();
    }

    public ResourceProxy<T> setHavingConnector(ConditionGroupConnector connector) {
        query.setHavingConnector(connector);
        return this;
    }

    public List<Condition> getHavingConditions() {
        return query.getHavingConditions();
    }

    public ResourceProxy<T> clearHavingConditions() {
        query.clearHavingConditions();
        return this;
    }

    public boolean hasHavingConditions() {
        return query.hasHavingConditions();
    }

    public ResourceProxy<T> having(Condition condition) {
        query.having(condition);
        return this;
    }

    public ConditionGroup getHavingConditionGroup() {
        return query.getHavingConditionGroup();
    }

    public ResourceProxy<T> having(String field, Object value) {
        query.having(field, value);
        return this;
    }

    public ResourceProxy<T> having(Field field, Object value) {
        query.having(field, value);
        return this;
    }

    public ResourceProxy<T> having(String field, ConditionOperator operator, Object value) {
        query.having(field, operator, value);
        return this;
    }

    public ResourceProxy<T> having(Field field, ConditionOperator operator, Object value) {
        query.having(field, operator, value);
        return this;
    }

    public ResourceProxy<T> havingGroup(ConditionGroup condition) {
        query.havingGroup(condition);
        return this;
    }

    public ResourceProxy<T> havingRaw(RawCondition condition) {
        query.havingRaw(condition);
        return this;
    }

    public ResourceProxy<T> havingField(String field1, String field2) {
        query.havingField(field1, field2);
        return this;
    }

    public ResourceProxy<T> havingField(Field field1, Field field2) {
        query.havingField(field1, field2);
        return this;
    }

    public ResourceProxy<T> havingField(String field1, ConditionOperator operator, String field2) {
        query.havingField(field1, operator, field2);
        return this;
    }

    public ResourceProxy<T> havingField(Field field1, ConditionOperator operator, Field field2) {
        query.havingField(field1, operator, field2);
        return this;
    }

    public ResourceProxy<T> havingNull(String field) {
        query.havingNull(field);
        return this;
    }

    public ResourceProxy<T> havingNull(Field field) {
        query.havingNull(field);
        return this;
    }

    public ResourceProxy<T> havingNotNull(String field) {
        query.havingNotNull(field);
        return this;
    }

    public ResourceProxy<T> havingNotNull(Field field) {
        query.havingNotNull(field);
        return this;
    }

    public ResourceProxy<T> havingIn(String field, Collection<Object> values) {
        query.havingIn(field, values);
        return this;
    }

    public ResourceProxy<T> havingIn(Field field, Collection<Object> values) {
        query.havingIn(field, values);
        return this;
    }

    public ResourceProxy<T> havingNotIn(String field, Collection<Object> values) {
        query.havingNotIn(field, values);
        return this;
    }

    public ResourceProxy<T> havingNotIn(Field field, Collection<Object> values) {
        query.havingNotIn(field, values);
        return this;
    }

    public ResourceProxy<T> havingContains(String field, Object value) {
        query.havingContains(field, value);
        return this;
    }

    public ResourceProxy<T> havingContains(Field field, Object value) {
        query.havingContains(field, value);
        return this;
    }

    public ResourceProxy<T> havingNotContains(String field, Object value) {
        query.havingNotContains(field, value);
        return this;
    }

    public ResourceProxy<T> havingNotContains(Field field, Object value) {
        query.havingNotContains(field, value);
        return this;
    }

    public ResourceProxy<T> havingGreaterThan(String field, Object value) {
        query.havingGreaterThan(field, value);
        return this;
    }

    public ResourceProxy<T> havingGreaterThan(Field field, Object value) {
        query.havingGreaterThan(field, value);
        return this;
    }

    public ResourceProxy<T> havingGreaterOrEqualsThan(String field, Object value) {
        query.havingGreaterOrEqualsThan(field, value);
        return this;
    }

    public ResourceProxy<T> havingGreaterOrEqualsThan(Field field, Object value) {
        query.havingGreaterOrEqualsThan(field, value);
        return this;
    }

    public ResourceProxy<T> havingLowerThan(String field, Object value) {
        query.havingLowerThan(field, value);
        return this;
    }

    public ResourceProxy<T> havingLowerThan(Field field, Object value) {
        query.havingLowerThan(field, value);
        return this;
    }

    public ResourceProxy<T> havingLowerOrEqualsThan(String field, Object value) {
        query.havingLowerOrEqualsThan(field, value);
        return this;
    }

    public ResourceProxy<T> havingLowerOrEqualsThan(Field field, Object value) {
        query.havingLowerOrEqualsThan(field, value);
        return this;
    }

    public ResourceProxy<T> join(Join join) {
        query.join(join);
        return this;
    }

    public ResourceProxy<T> join(String tableName, String field1, String field2) {
        query.join(tableName, field1, field2);
        return this;
    }

    public ResourceProxy<T> join(String tableName, Field field1, Field field2) {
        query.join(tableName, field1, field2);
        return this;
    }

    public ResourceProxy<T> innerJoin(String tableName, String field1, String field2) {
        query.innerJoin(tableName, field1, field2);
        return this;
    }

    public ResourceProxy<T> innerJoin(String tableName, Field field1, Field field2) {
        query.innerJoin(tableName, field1, field2);
        return this;
    }

    public ResourceProxy<T> leftJoin(String tableName, String field1, String field2) {
        query.leftJoin(tableName, field1, field2);
        return this;
    }

    public ResourceProxy<T> leftJoin(String tableName, Field field1, Field field2) {
        query.leftJoin(tableName, field1, field2);
        return this;
    }

    public ResourceProxy<T> rightJoin(String tableName, String field1, String field2) {
        query.rightJoin(tableName, field1, field2);
        return this;
    }

    public ResourceProxy<T> rightJoin(String tableName, Field field1, Field field2) {
        query.rightJoin(tableName, field1, field2);
        return this;
    }

    public List<Join> getJoins() {
        return query.getJoins();
    }
}