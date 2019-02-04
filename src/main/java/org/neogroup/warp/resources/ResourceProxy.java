package org.neogroup.warp.resources;

import org.neogroup.warp.query.Query;
import org.neogroup.warp.query.conditions.*;
import org.neogroup.warp.query.fields.Field;
import org.neogroup.warp.query.fields.SelectField;
import org.neogroup.warp.query.fields.SortDirection;
import org.neogroup.warp.query.fields.SortField;
import org.neogroup.warp.query.joins.Join;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ResourceProxy<T extends Object> {

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
        return (T)limit(1).find().iterator().next();
    }

    public ResourceProxy select(String... fields) {
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

    public ResourceProxy setDistinct(boolean distinct) {
        query.setDistinct(distinct);
        return this;
    }

    public Integer getLimit() {
        return query.getLimit();
    }

    public ResourceProxy limit(Integer limit) {
        query.limit(limit);
        return this;
    }

    public Integer getOffset() {
        return query.getOffset();
    }

    public ResourceProxy offset(Integer offset) {
        query.offset(offset);
        return this;
    }

    public ResourceProxy select(SelectField... fields) {
        query.select(fields);
        return this;
    }

    public ResourceProxy selectField(SelectField field) {
        query.selectField(field);
        return this;
    }

    public ResourceProxy selectField(String name) {
        query.selectField(name);
        return this;
    }

    public ResourceProxy selectField(String name, String alias) {
        query.selectField(name, alias);
        return this;
    }

    public ResourceProxy selectField(String tableName, String name, String alias) {
        query.selectField(tableName, name, alias);
        return this;
    }

    public List<SelectField> getSelectFields() {
        return query.getSelectFields();
    }

    public ResourceProxy clearSelectFields() {
        query.clearSelectFields();
        return this;
    }

    public ResourceProxy groupBy(String... groupByFields) {
        query.groupBy(groupByFields);
        return this;
    }

    public ResourceProxy groupBy(Field... groupByFields) {
        query.groupBy(groupByFields);
        return this;
    }

    public List<Field> getGroupByFields() {
        return query.getGroupByFields();
    }

    public ResourceProxy clearGroupByFields() {
        query.clearGroupByFields();
        return this;
    }

    public ResourceProxy orderBy(String... orderByFields) {
        query.orderBy(orderByFields);
        return this;
    }

    public ResourceProxy orderBy(SortField... orderByFields) {
        query.orderBy(orderByFields);
        return this;
    }

    public ResourceProxy orderBy(String field, SortDirection direction) {
        query.orderBy(field, direction);
        return this;
    }

    public List<SortField> getOrderByFields() {
        return query.getOrderByFields();
    }

    public ResourceProxy clearOrderByFields() {
        query.clearOrderByFields();
        return this;
    }

    public ResourceProxy set(String field, Object value) {
        query.set(field, value);
        return this;
    }

    public <V> V get(String field) {
        return query.get(field);
    }

    public Map<String, Object> getFields() {
        return query.getFields();
    }

    public ResourceProxy clearFields() {
        query.clearFields();
        return this;
    }

    public ConditionGroupConnector getWhereConnector() {
        return query.getWhereConnector();
    }

    public ResourceProxy setWhereConnector(ConditionGroupConnector connector) {
        query.setWhereConnector(connector);
        return this;
    }

    public List<Condition> getWhereConditions() {
        return query.getWhereConditions();
    }

    public ResourceProxy clearWhereConditions() {
        query.clearWhereConditions();
        return this;
    }

    public boolean hasWhereConditions() {
        return query.hasWhereConditions();
    }

    public ResourceProxy where(Condition condition) {
        query.where(condition);
        return this;
    }

    public ConditionGroup getWhereConditionGroup() {
        return query.getWhereConditionGroup();
    }

    public ResourceProxy where(String field, Object value) {
        query.where(field, value);
        return this;
    }

    public ResourceProxy where(Field field, Object value) {
        query.where(field, value);
        return this;
    }

    public ResourceProxy where(String field, ConditionOperator operator, Object value) {
        query.where(field, operator, value);
        return this;
    }

    public ResourceProxy where(Field field, ConditionOperator operator, Object value) {
        query.where(field, operator, value);
        return this;
    }

    public ResourceProxy whereGroup(ConditionGroup condition) {
        query.whereGroup(condition);
        return this;
    }

    public ResourceProxy whereRaw(RawCondition condition) {
        query.whereRaw(condition);
        return this;
    }

    public ResourceProxy whereField(String field1, String field2) {
        query.whereField(field1, field2);
        return this;
    }

    public ResourceProxy whereField(Field field1, Field field2) {
        query.whereField(field1, field2);
        return this;
    }

    public ResourceProxy whereField(String field1, ConditionOperator operator, String field2) {
        query.whereField(field1, operator, field2);
        return this;
    }

    public ResourceProxy whereField(Field field1, ConditionOperator operator, Field field2) {
        query.whereField(field1, operator, field2);
        return this;
    }

    public ResourceProxy whereNull(String field) {
        query.whereNull(field);
        return this;
    }

    public ResourceProxy whereNull(Field field) {
        query.whereNull(field);
        return this;
    }

    public ResourceProxy whereNotNull(String field) {
        query.whereNotNull(field);
        return this;
    }

    public ResourceProxy whereNotNull(Field field) {
        query.whereNotNull(field);
        return this;
    }

    public ResourceProxy whereIn(String field, Collection<Object> values) {
        query.whereIn(field, values);
        return this;
    }

    public ResourceProxy whereIn(Field field, Collection<Object> values) {
        query.whereIn(field, values);
        return this;
    }

    public ResourceProxy whereNotIn(String field, Collection<Object> values) {
        query.whereNotIn(field, values);
        return this;
    }

    public ResourceProxy whereNotIn(Field field, Collection<Object> values) {
        query.whereNotIn(field, values);
        return this;
    }

    public ResourceProxy whereContains(String field, Object value) {
        query.whereContains(field, value);
        return this;
    }

    public ResourceProxy whereContains(Field field, Object value) {
        query.whereContains(field, value);
        return this;
    }

    public ResourceProxy whereNotContains(String field, Object value) {
        query.whereNotContains(field, value);
        return this;
    }

    public ResourceProxy whereNotContains(Field field, Object value) {
        query.whereNotContains(field, value);
        return this;
    }

    public ResourceProxy whereGreaterThan(String field, Object value) {
        query.whereGreaterThan(field, value);
        return this;
    }

    public ResourceProxy whereGreaterThan(Field field, Object value) {
        query.whereGreaterThan(field, value);
        return this;
    }

    public ResourceProxy whereGreaterOrEqualsThan(String field, Object value) {
        query.whereGreaterOrEqualsThan(field, value);
        return this;
    }

    public ResourceProxy whereGreaterOrEqualsThan(Field field, Object value) {
        query.whereGreaterOrEqualsThan(field, value);
        return this;
    }

    public ResourceProxy whereLowerThan(String field, Object value) {
        query.whereLowerThan(field, value);
        return this;
    }

    public ResourceProxy whereLowerThan(Field field, Object value) {
        query.whereLowerThan(field, value);
        return this;
    }

    public ResourceProxy whereLowerOrEqualsThan(String field, Object value) {
        query.whereLowerOrEqualsThan(field, value);
        return this;
    }

    public ResourceProxy whereLowerOrEqualsThan(Field field, Object value) {
        query.whereLowerOrEqualsThan(field, value);
        return this;
    }

    public ConditionGroupConnector getHavingConnector() {
        return query.getHavingConnector();
    }

    public ResourceProxy setHavingConnector(ConditionGroupConnector connector) {
        query.setHavingConnector(connector);
        return this;
    }

    public List<Condition> getHavingConditions() {
        return query.getHavingConditions();
    }

    public ResourceProxy clearHavingConditions() {
        query.clearHavingConditions();
        return this;
    }

    public boolean hasHavingConditions() {
        return query.hasHavingConditions();
    }

    public ResourceProxy having(Condition condition) {
        query.having(condition);
        return this;
    }

    public ConditionGroup getHavingConditionGroup() {
        return query.getHavingConditionGroup();
    }

    public ResourceProxy having(String field, Object value) {
        query.having(field, value);
        return this;
    }

    public ResourceProxy having(Field field, Object value) {
        query.having(field, value);
        return this;
    }

    public ResourceProxy having(String field, ConditionOperator operator, Object value) {
        query.having(field, operator, value);
        return this;
    }

    public ResourceProxy having(Field field, ConditionOperator operator, Object value) {
        query.having(field, operator, value);
        return this;
    }

    public ResourceProxy havingGroup(ConditionGroup condition) {
        query.havingGroup(condition);
        return this;
    }

    public ResourceProxy havingRaw(RawCondition condition) {
        query.havingRaw(condition);
        return this;
    }

    public ResourceProxy havingField(String field1, String field2) {
        query.havingField(field1, field2);
        return this;
    }

    public ResourceProxy havingField(Field field1, Field field2) {
        query.havingField(field1, field2);
        return this;
    }

    public ResourceProxy havingField(String field1, ConditionOperator operator, String field2) {
        query.havingField(field1, operator, field2);
        return this;
    }

    public ResourceProxy havingField(Field field1, ConditionOperator operator, Field field2) {
        query.havingField(field1, operator, field2);
        return this;
    }

    public ResourceProxy havingNull(String field) {
        query.havingNull(field);
        return this;
    }

    public ResourceProxy havingNull(Field field) {
        query.havingNull(field);
        return this;
    }

    public ResourceProxy havingNotNull(String field) {
        query.havingNotNull(field);
        return this;
    }

    public ResourceProxy havingNotNull(Field field) {
        query.havingNotNull(field);
        return this;
    }

    public ResourceProxy havingIn(String field, Collection<Object> values) {
        query.havingIn(field, values);
        return this;
    }

    public ResourceProxy havingIn(Field field, Collection<Object> values) {
        query.havingIn(field, values);
        return this;
    }

    public ResourceProxy havingNotIn(String field, Collection<Object> values) {
        query.havingNotIn(field, values);
        return this;
    }

    public ResourceProxy havingNotIn(Field field, Collection<Object> values) {
        query.havingNotIn(field, values);
        return this;
    }

    public ResourceProxy havingContains(String field, Object value) {
        query.havingContains(field, value);
        return this;
    }

    public ResourceProxy havingContains(Field field, Object value) {
        query.havingContains(field, value);
        return this;
    }

    public ResourceProxy havingNotContains(String field, Object value) {
        query.havingNotContains(field, value);
        return this;
    }

    public ResourceProxy havingNotContains(Field field, Object value) {
        query.havingNotContains(field, value);
        return this;
    }

    public ResourceProxy havingGreaterThan(String field, Object value) {
        query.havingGreaterThan(field, value);
        return this;
    }

    public ResourceProxy havingGreaterThan(Field field, Object value) {
        query.havingGreaterThan(field, value);
        return this;
    }

    public ResourceProxy havingGreaterOrEqualsThan(String field, Object value) {
        query.havingGreaterOrEqualsThan(field, value);
        return this;
    }

    public ResourceProxy havingGreaterOrEqualsThan(Field field, Object value) {
        query.havingGreaterOrEqualsThan(field, value);
        return this;
    }

    public ResourceProxy havingLowerThan(String field, Object value) {
        query.havingLowerThan(field, value);
        return this;
    }

    public ResourceProxy havingLowerThan(Field field, Object value) {
        query.havingLowerThan(field, value);
        return this;
    }

    public ResourceProxy havingLowerOrEqualsThan(String field, Object value) {
        query.havingLowerOrEqualsThan(field, value);
        return this;
    }

    public ResourceProxy havingLowerOrEqualsThan(Field field, Object value) {
        query.havingLowerOrEqualsThan(field, value);
        return this;
    }

    public ResourceProxy join(Join join) {
        query.join(join);
        return this;
    }

    public ResourceProxy join(String tableName, String field1, String field2) {
        query.join(tableName, field1, field2);
        return this;
    }

    public ResourceProxy join(String tableName, Field field1, Field field2) {
        query.join(tableName, field1, field2);
        return this;
    }

    public ResourceProxy innerJoin(String tableName, String field1, String field2) {
        query.innerJoin(tableName, field1, field2);
        return this;
    }

    public ResourceProxy innerJoin(String tableName, Field field1, Field field2) {
        query.innerJoin(tableName, field1, field2);
        return this;
    }

    public ResourceProxy leftJoin(String tableName, String field1, String field2) {
        query.leftJoin(tableName, field1, field2);
        return this;
    }

    public ResourceProxy leftJoin(String tableName, Field field1, Field field2) {
        query.leftJoin(tableName, field1, field2);
        return this;
    }

    public ResourceProxy rightJoin(String tableName, String field1, String field2) {
        query.rightJoin(tableName, field1, field2);
        return this;
    }

    public ResourceProxy rightJoin(String tableName, Field field1, Field field2) {
        query.rightJoin(tableName, field1, field2);
        return this;
    }

    public List<Join> getJoins() {
        return query.getJoins();
    }
}