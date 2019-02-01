
package org.neogroup.warp.query;

import org.neogroup.warp.query.conditions.*;

import java.util.*;

public class Query {

    private List<SelectField> selectFields;
    private List<Field> groupByFields;
    private Map<String,Object> fields;
    private ConditionGroup whereConditionGroup;
    private ConditionGroup havingConditionGroup;

    public Query() {
        this.selectFields = new ArrayList<>();
        this.groupByFields = new ArrayList<>();
        this.fields = new HashMap<>();
        this.whereConditionGroup = new ConditionGroup();
        this.havingConditionGroup = new ConditionGroup();
    }

    public Query select(String... fields) {
        for (String field : fields) {
            selectFields.add(new SelectField(field));
        }
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

    public ConditionGroup clearWhereConditions() {
        return whereConditionGroup.clearConditions();
    }

    public boolean hasWhereConditions() {
        return whereConditionGroup.isEmpty();
    }

    public ConditionGroup addWhereCondition(Condition condition) {
        return whereConditionGroup.addCondition(condition);
    }

    public ConditionGroup where(String field, Object value) {
        return whereConditionGroup.on(field, value);
    }

    public ConditionGroup where(Field field, Object value) {
        return whereConditionGroup.on(field, value);
    }

    public ConditionGroup where(String field, ConditionOperator operator, Object value) {
        return whereConditionGroup.on(field, operator, value);
    }

    public ConditionGroup where(Field field, ConditionOperator operator, Object value) {
        return whereConditionGroup.on(field, operator, value);
    }

    public ConditionGroup whereGroup(ConditionGroup condition) {
        return whereConditionGroup.onGroup(condition);
    }

    public ConditionGroup whereRaw(RawCondition condition) {
        return whereConditionGroup.onRaw(condition);
    }

    public ConditionGroup whereField(String field1, String field2) {
        return whereConditionGroup.onField(field1, field2);
    }

    public ConditionGroup whereField(Field field1, Field field2) {
        return whereConditionGroup.onField(field1, field2);
    }

    public ConditionGroup whereField(String field1, ConditionOperator operator, String field2) {
        return whereConditionGroup.onField(field1, operator, field2);
    }

    public ConditionGroup whereField(Field field1, ConditionOperator operator, Field field2) {
        return whereConditionGroup.onField(field1, operator, field2);
    }

    public ConditionGroup whereNull(String field) {
        return whereConditionGroup.onNull(field);
    }

    public ConditionGroup whereNull(Field field) {
        return whereConditionGroup.onNull(field);
    }

    public ConditionGroup whereNotNull(String field) {
        return whereConditionGroup.onNotNull(field);
    }

    public ConditionGroup whereNotNull(Field field) {
        return whereConditionGroup.onNotNull(field);
    }

    public ConditionGroup whereIn(String field, Collection<Object> values) {
        return whereConditionGroup.onIn(field, values);
    }

    public ConditionGroup whereIn(Field field, Collection<Object> values) {
        return whereConditionGroup.onIn(field, values);
    }

    public ConditionGroup whereNotIn(String field, Collection<Object> values) {
        return whereConditionGroup.onNotIn(field, values);
    }

    public ConditionGroup whereNotIn(Field field, Collection<Object> values) {
        return whereConditionGroup.onNotIn(field, values);
    }

    public ConditionGroup whereContains(String field, Object value) {
        return whereConditionGroup.onContains(field, value);
    }

    public ConditionGroup whereContains(Field field, Object value) {
        return whereConditionGroup.onContains(field, value);
    }

    public ConditionGroup whereNotContains(String field, Object value) {
        return whereConditionGroup.onNotContains(field, value);
    }

    public ConditionGroup whereNotContains(Field field, Object value) {
        return whereConditionGroup.onNotContains(field, value);
    }

    public ConditionGroup whereGreaterThan(String field, Object value) {
        return whereConditionGroup.onGreaterThan(field, value);
    }

    public ConditionGroup whereGreaterThan(Field field, Object value) {
        return whereConditionGroup.onGreaterThan(field, value);
    }

    public ConditionGroup whereGreaterOrEqualsThan(String field, Object value) {
        return whereConditionGroup.onGreaterOrEqualsThan(field, value);
    }

    public ConditionGroup whereGreaterOrEqualsThan(Field field, Object value) {
        return whereConditionGroup.onGreaterOrEqualsThan(field, value);
    }

    public ConditionGroup whereLowerThan(String field, Object value) {
        return whereConditionGroup.onLowerThan(field, value);
    }

    public ConditionGroup whereLowerThan(Field field, Object value) {
        return whereConditionGroup.onLowerThan(field, value);
    }

    public ConditionGroup whereLowerOrEqualsThan(String field, Object value) {
        return whereConditionGroup.onLowerOrEqualsThan(field, value);
    }

    public ConditionGroup whereLowerOrEqualsThan(Field field, Object value) {
        return whereConditionGroup.onLowerOrEqualsThan(field, value);
    }

    public ConditionGroupConnector getHavingConnector() {
        return havingConditionGroup.getConnector();
    }

    public void setHavingConnector(ConditionGroupConnector connector) {
        havingConditionGroup.setConnector(connector);
    }

    public List<Condition> getHavingConditions() {
        return havingConditionGroup.getConditions();
    }

    public ConditionGroup clearHavingConditions() {
        return havingConditionGroup.clearConditions();
    }

    public boolean hasHavingConditions() {
        return havingConditionGroup.isEmpty();
    }

    public ConditionGroup addHavingCondition(Condition condition) {
        return havingConditionGroup.addCondition(condition);
    }

    public ConditionGroup having(String field, Object value) {
        return havingConditionGroup.on(field, value);
    }

    public ConditionGroup having(Field field, Object value) {
        return havingConditionGroup.on(field, value);
    }

    public ConditionGroup having(String field, ConditionOperator operator, Object value) {
        return havingConditionGroup.on(field, operator, value);
    }

    public ConditionGroup having(Field field, ConditionOperator operator, Object value) {
        return havingConditionGroup.on(field, operator, value);
    }

    public ConditionGroup havingGroup(ConditionGroup condition) {
        return havingConditionGroup.onGroup(condition);
    }

    public ConditionGroup havingRaw(RawCondition condition) {
        return havingConditionGroup.onRaw(condition);
    }

    public ConditionGroup havingField(String field1, String field2) {
        return havingConditionGroup.onField(field1, field2);
    }

    public ConditionGroup havingField(Field field1, Field field2) {
        return havingConditionGroup.onField(field1, field2);
    }

    public ConditionGroup havingField(String field1, ConditionOperator operator, String field2) {
        return havingConditionGroup.onField(field1, operator, field2);
    }

    public ConditionGroup havingField(Field field1, ConditionOperator operator, Field field2) {
        return havingConditionGroup.onField(field1, operator, field2);
    }

    public ConditionGroup havingNull(String field) {
        return havingConditionGroup.onNull(field);
    }

    public ConditionGroup havingNull(Field field) {
        return havingConditionGroup.onNull(field);
    }

    public ConditionGroup havingNotNull(String field) {
        return havingConditionGroup.onNotNull(field);
    }

    public ConditionGroup havingNotNull(Field field) {
        return havingConditionGroup.onNotNull(field);
    }

    public ConditionGroup havingIn(String field, Collection<Object> values) {
        return havingConditionGroup.onIn(field, values);
    }

    public ConditionGroup havingIn(Field field, Collection<Object> values) {
        return havingConditionGroup.onIn(field, values);
    }

    public ConditionGroup havingNotIn(String field, Collection<Object> values) {
        return havingConditionGroup.onNotIn(field, values);
    }

    public ConditionGroup havingNotIn(Field field, Collection<Object> values) {
        return havingConditionGroup.onNotIn(field, values);
    }

    public ConditionGroup havingContains(String field, Object value) {
        return havingConditionGroup.onContains(field, value);
    }

    public ConditionGroup havingContains(Field field, Object value) {
        return havingConditionGroup.onContains(field, value);
    }

    public ConditionGroup havingNotContains(String field, Object value) {
        return havingConditionGroup.onNotContains(field, value);
    }

    public ConditionGroup havingNotContains(Field field, Object value) {
        return havingConditionGroup.onNotContains(field, value);
    }

    public ConditionGroup havingGreaterThan(String field, Object value) {
        return havingConditionGroup.onGreaterThan(field, value);
    }

    public ConditionGroup havingGreaterThan(Field field, Object value) {
        return havingConditionGroup.onGreaterThan(field, value);
    }

    public ConditionGroup havingGreaterOrEqualsThan(String field, Object value) {
        return havingConditionGroup.onGreaterOrEqualsThan(field, value);
    }

    public ConditionGroup havingGreaterOrEqualsThan(Field field, Object value) {
        return havingConditionGroup.onGreaterOrEqualsThan(field, value);
    }

    public ConditionGroup havingLowerThan(String field, Object value) {
        return havingConditionGroup.onLowerThan(field, value);
    }

    public ConditionGroup havingLowerThan(Field field, Object value) {
        return havingConditionGroup.onLowerThan(field, value);
    }

    public ConditionGroup havingLowerOrEqualsThan(String field, Object value) {
        return havingConditionGroup.onLowerOrEqualsThan(field, value);
    }

    public ConditionGroup havingLowerOrEqualsThan(Field field, Object value) {
        return havingConditionGroup.onLowerOrEqualsThan(field, value);
    }
}