
package org.neogroup.warp.query;

import org.neogroup.warp.query.conditions.*;
import org.neogroup.warp.query.fields.Field;
import org.neogroup.warp.query.fields.SelectField;
import org.neogroup.warp.query.fields.SortDirection;
import org.neogroup.warp.query.fields.SortField;
import org.neogroup.warp.query.joins.Join;
import org.neogroup.warp.query.joins.JoinType;

import java.util.*;

public class Query {

    private String tableName;
    private String tableAlias;
    private List<SelectField> selectFields;
    private List<Field> groupByFields;
    private List<SortField> orderByFields;
    private Map<String,Object> fields;
    private ConditionGroup whereConditionGroup;
    private ConditionGroup havingConditionGroup;
    private List<Join> joins;
    private boolean distinct;
    private Integer limit;
    private Integer offset;

    public Query(String tableName) {
        this(tableName, null);
    }

    public Query(String tableName, String tableAlias) {
        this.tableName = tableName;
        this.tableAlias = tableAlias;
        this.selectFields = new ArrayList<>();
        this.groupByFields = new ArrayList<>();
        this.orderByFields = new ArrayList<>();
        this.fields = new HashMap<>();
        this.whereConditionGroup = new ConditionGroup();
        this.havingConditionGroup = new ConditionGroup();
        this.joins = new ArrayList<>();
        this.limit = null;
        this.offset = null;
        this.distinct = false;
    }

    public Query select(String... fields) {
        for (String field : fields) {
            selectFields.add(new SelectField(field));
        }
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public Query setDistinct(boolean distinct) {
        this.distinct = distinct;
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public Query limit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public Integer getOffset() {
        return offset;
    }

    public Query offset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public Query select(SelectField... fields) {
        Collections.addAll(selectFields, fields);
        return this;
    }

    public Query selectField (SelectField field) {
        selectFields.add (field);
        return this;
    }

    public Query selectField (String name) {
        return selectField(name, null);
    }

    public Query selectField (String name, String alias) {
        return selectField(null, name, alias);
    }

    public Query selectField (String tableName, String name, String alias) {
        return selectField(new SelectField(tableName, name, alias));
    }

    public List<SelectField> getSelectFields () {
        return selectFields;
    }

    public Query clearSelectFields () {
        selectFields.clear();
        return this;
    }

    public Query groupBy(String... groupByFields) {
        for (String groupByField : groupByFields) {
            groupBy(new Field(groupByField));
        }
        return this;
    }

    public Query groupBy(Field... groupByFields) {
        Collections.addAll(this.groupByFields, groupByFields);
        return this;
    }

    public List<Field> getGroupByFields() {
        return groupByFields;
    }

    public Query clearGroupByFields() {
        groupByFields.clear();
        return this;
    }

    public Query orderBy(String... orderByFields) {
        for (String orderByField : orderByFields) {
            orderBy(new SortField(orderByField));
        }
        return this;
    }

    public Query orderBy(SortField... orderByFields) {
        Collections.addAll(this.orderByFields, orderByFields);
        return this;
    }

    public Query orderBy(String field, SortDirection direction) {
        return orderBy(new SortField(field, direction));
    }

    public List<SortField> getOrderByFields() {
        return orderByFields;
    }

    public Query clearOrderByFields() {
        orderByFields.clear();
        return this;
    }

    public Query set (String field, Object value) {
        fields.put(field, value);
        return this;
    }

    public <V extends Object> V get (String field) {
        return (V)fields.get(field);
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public Query clearFields () {
        fields.clear();
        return this;
    }

    public ConditionGroupConnector getWhereConnector() {
        return whereConditionGroup.getConnector();
    }

    public void setWhereConnector(ConditionGroupConnector connector) {
        whereConditionGroup.setConnector(connector);
    }

    public List<Condition> getWhereConditions() {
        return whereConditionGroup.getConditions();
    }

    public Query clearWhereConditions() {
        whereConditionGroup.clearConditions();
        return this;
    }

    public boolean hasWhereConditions() {
        return whereConditionGroup.isEmpty();
    }

    public Query where(Condition condition) {
        whereConditionGroup.addCondition(condition);
        return this;
    }

    public ConditionGroup getWhereConditionGroup() {
        return whereConditionGroup;
    }

    public Query where(String field, Object value) {
        whereConditionGroup.on(field, value);
        return this;
    }

    public Query where(Field field, Object value) {
        whereConditionGroup.on(field, value);
        return this;
    }

    public Query where(String field, ConditionOperator operator, Object value) {
        whereConditionGroup.on(field, operator, value);
        return this;
    }

    public Query where(Field field, ConditionOperator operator, Object value) {
        whereConditionGroup.on(field, operator, value);
        return this;
    }

    public Query whereGroup(ConditionGroup condition) {
        whereConditionGroup.onGroup(condition);
        return this;
    }

    public Query whereRaw(RawCondition condition) {
        whereConditionGroup.onRaw(condition);
        return this;
    }

    public Query whereField(String field1, String field2) {
        whereConditionGroup.onField(field1, field2);
        return this;
    }

    public Query whereField(Field field1, Field field2) {
        whereConditionGroup.onField(field1, field2);
        return this;
    }

    public Query whereField(String field1, ConditionOperator operator, String field2) {
        whereConditionGroup.onField(field1, operator, field2);
        return this;
    }

    public Query whereField(Field field1, ConditionOperator operator, Field field2) {
        whereConditionGroup.onField(field1, operator, field2);
        return this;
    }

    public Query whereNull(String field) {
        whereConditionGroup.onNull(field);
        return this;
    }

    public Query whereNull(Field field) {
        whereConditionGroup.onNull(field);
        return this;
    }

    public Query whereNotNull(String field) {
        whereConditionGroup.onNotNull(field);
        return this;
    }

    public Query whereNotNull(Field field) {
        whereConditionGroup.onNotNull(field);
        return this;
    }

    public Query whereIn(String field, Collection<Object> values) {
        whereConditionGroup.onIn(field, values);
        return this;
    }

    public Query whereIn(Field field, Collection<Object> values) {
        whereConditionGroup.onIn(field, values);
        return this;
    }

    public Query whereNotIn(String field, Collection<Object> values) {
        whereConditionGroup.onNotIn(field, values);
        return this;
    }

    public Query whereNotIn(Field field, Collection<Object> values) {
        whereConditionGroup.onNotIn(field, values);
        return this;
    }

    public Query whereContains(String field, Object value) {
        whereConditionGroup.onContains(field, value);
        return this;
    }

    public Query whereContains(Field field, Object value) {
        whereConditionGroup.onContains(field, value);
        return this;
    }

    public Query whereNotContains(String field, Object value) {
        whereConditionGroup.onNotContains(field, value);
        return this;
    }

    public Query whereNotContains(Field field, Object value) {
        whereConditionGroup.onNotContains(field, value);
        return this;
    }

    public Query whereGreaterThan(String field, Object value) {
        whereConditionGroup.onGreaterThan(field, value);
        return this;
    }

    public Query whereGreaterThan(Field field, Object value) {
        whereConditionGroup.onGreaterThan(field, value);
        return this;
    }

    public Query whereGreaterOrEqualsThan(String field, Object value) {
        whereConditionGroup.onGreaterOrEqualsThan(field, value);
        return this;
    }

    public Query whereGreaterOrEqualsThan(Field field, Object value) {
        whereConditionGroup.onGreaterOrEqualsThan(field, value);
        return this;
    }

    public Query whereLowerThan(String field, Object value) {
        whereConditionGroup.onLowerThan(field, value);
        return this;
    }

    public Query whereLowerThan(Field field, Object value) {
        whereConditionGroup.onLowerThan(field, value);
        return this;
    }

    public Query whereLowerOrEqualsThan(String field, Object value) {
        whereConditionGroup.onLowerOrEqualsThan(field, value);
        return this;
    }

    public Query whereLowerOrEqualsThan(Field field, Object value) {
        whereConditionGroup.onLowerOrEqualsThan(field, value);
        return this;
    }

    public ConditionGroupConnector getHavingConnector() {
        return havingConditionGroup.getConnector();
    }

    public Query setHavingConnector(ConditionGroupConnector connector) {
        havingConditionGroup.setConnector(connector);
        return this;
    }

    public List<Condition> getHavingConditions() {
        return havingConditionGroup.getConditions();
    }

    public Query clearHavingConditions() {
        havingConditionGroup.clearConditions();
        return this;
    }

    public boolean hasHavingConditions() {
        return havingConditionGroup.isEmpty();
    }

    public Query having(Condition condition) {
        havingConditionGroup.addCondition(condition);
        return this;
    }

    public ConditionGroup getHavingConditionGroup() {
        return havingConditionGroup;
    }

    public Query having(String field, Object value) {
        havingConditionGroup.on(field, value);
        return this;
    }

    public Query having(Field field, Object value) {
        havingConditionGroup.on(field, value);
        return this;
    }

    public Query having(String field, ConditionOperator operator, Object value) {
        havingConditionGroup.on(field, operator, value);
        return this;
    }

    public Query having(Field field, ConditionOperator operator, Object value) {
        havingConditionGroup.on(field, operator, value);
        return this;
    }

    public Query havingGroup(ConditionGroup condition) {
        havingConditionGroup.onGroup(condition);
        return this;
    }

    public Query havingRaw(RawCondition condition) {
        havingConditionGroup.onRaw(condition);
        return this;
    }

    public Query havingField(String field1, String field2) {
        havingConditionGroup.onField(field1, field2);
        return this;
    }

    public Query havingField(Field field1, Field field2) {
        havingConditionGroup.onField(field1, field2);
        return this;
    }

    public Query havingField(String field1, ConditionOperator operator, String field2) {
        havingConditionGroup.onField(field1, operator, field2);
        return this;
    }

    public Query havingField(Field field1, ConditionOperator operator, Field field2) {
        havingConditionGroup.onField(field1, operator, field2);
        return this;
    }

    public Query havingNull(String field) {
        havingConditionGroup.onNull(field);
        return this;
    }

    public Query havingNull(Field field) {
        havingConditionGroup.onNull(field);
        return this;
    }

    public Query havingNotNull(String field) {
        havingConditionGroup.onNotNull(field);
        return this;
    }

    public Query havingNotNull(Field field) {
        havingConditionGroup.onNotNull(field);
        return this;
    }

    public Query havingIn(String field, Collection<Object> values) {
        havingConditionGroup.onIn(field, values);
        return this;
    }

    public Query havingIn(Field field, Collection<Object> values) {
        havingConditionGroup.onIn(field, values);
        return this;
    }

    public Query havingNotIn(String field, Collection<Object> values) {
        havingConditionGroup.onNotIn(field, values);
        return this;
    }

    public Query havingNotIn(Field field, Collection<Object> values) {
        havingConditionGroup.onNotIn(field, values);
        return this;
    }

    public Query havingContains(String field, Object value) {
        havingConditionGroup.onContains(field, value);
        return this;
    }

    public Query havingContains(Field field, Object value) {
        havingConditionGroup.onContains(field, value);
        return this;
    }

    public Query havingNotContains(String field, Object value) {
        havingConditionGroup.onNotContains(field, value);
        return this;
    }

    public Query havingNotContains(Field field, Object value) {
        havingConditionGroup.onNotContains(field, value);
        return this;
    }

    public Query havingGreaterThan(String field, Object value) {
        havingConditionGroup.onGreaterThan(field, value);
        return this;
    }

    public Query havingGreaterThan(Field field, Object value) {
        havingConditionGroup.onGreaterThan(field, value);
        return this;
    }

    public Query havingGreaterOrEqualsThan(String field, Object value) {
        havingConditionGroup.onGreaterOrEqualsThan(field, value);
        return this;
    }

    public Query havingGreaterOrEqualsThan(Field field, Object value) {
        havingConditionGroup.onGreaterOrEqualsThan(field, value);
        return this;
    }

    public Query havingLowerThan(String field, Object value) {
        havingConditionGroup.onLowerThan(field, value);
        return this;
    }

    public Query havingLowerThan(Field field, Object value) {
        havingConditionGroup.onLowerThan(field, value);
        return this;
    }

    public Query havingLowerOrEqualsThan(String field, Object value) {
        havingConditionGroup.onLowerOrEqualsThan(field, value);
        return this;
    }

    public Query havingLowerOrEqualsThan(Field field, Object value) {
        havingConditionGroup.onLowerOrEqualsThan(field, value);
        return this;
    }

    public Query join (Join join) {
        joins.add(join);
        return this;
    }

    public Query join (String tableName, String field1, String field2) {
        Join join = new Join(tableName, JoinType.JOIN);
        join.onField(field1, field2);
        return join(join);
    }

    public Query join (String tableName, Field field1, Field field2) {
        Join join = new Join(tableName, JoinType.JOIN);
        join.onField(field1, field2);
        return join(join);
    }

    public Query innerJoin (String tableName, String field1, String field2) {
        Join join = new Join(tableName, JoinType.INNER_JOIN);
        join.onField(field1, field2);
        return join(join);
    }

    public Query innerJoin (String tableName, Field field1, Field field2) {
        Join join = new Join(tableName, JoinType.INNER_JOIN);
        join.onField(field1, field2);
        return join(join);
    }

    public Query leftJoin (String tableName, String field1, String field2) {
        Join join = new Join(tableName, JoinType.LEFT_JOIN);
        join.onField(field1, field2);
        return join(join);
    }

    public Query leftJoin (String tableName, Field field1, Field field2) {
        Join join = new Join(tableName, JoinType.LEFT_JOIN);
        join.onField(field1, field2);
        return join(join);
    }

    public Query rightJoin (String tableName, String field1, String field2) {
        Join join = new Join(tableName, JoinType.RIGHT_JOIN);
        join.onField(field1, field2);
        return join(join);
    }

    public Query rightJoin (String tableName, Field field1, Field field2) {
        Join join = new Join(tableName, JoinType.RIGHT_JOIN);
        join.onField(field1, field2);
        return join(join);
    }

    public List<Join> getJoins () {
        return joins;
    }
}