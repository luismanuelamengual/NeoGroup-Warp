package org.neogroup.warp.data.query;

import org.neogroup.warp.data.query.conditions.*;
import org.neogroup.warp.data.query.fields.*;
import org.neogroup.warp.data.query.joins.Join;
import org.neogroup.warp.data.query.joins.JoinType;

import java.util.ArrayList;
import java.util.List;

public class Select extends Query {

    private final String tableName;
    private final List<SelectField> selectFields;
    private final ConditionGroup whereConditionGroup;
    private final ConditionGroup havingConditionGroup;
    private final List<OrderByField> orderByFields;
    private final List<Field> groupByFields;
    private final List<Join> joins;
    private Integer limit;
    private Integer offset;

    public Select(String tableName) {
        this.tableName = tableName;
        selectFields = new ArrayList<>();
        whereConditionGroup = new ConditionGroup();
        havingConditionGroup = new ConditionGroup();
        joins = new ArrayList<>();
        orderByFields = new ArrayList<>();
        groupByFields = new ArrayList<>();
    }

    public String getTableName() {
        return tableName;
    }

    public Select clearSelectFields() {
        selectFields.clear();
        return this;
    }

    public List<SelectField> getSelectFields() {
        return selectFields;
    }

    public Select addSelectField (String rawField) {
        addSelectField(rawField, null);
        return this;
    }

    public Select addSelectField (String rawField, String alias) {
        addSelectField(new SelectField(new RawField(rawField), alias));
        return this;
    }

    public Select addSelectField (String tableName, String columnName, String alias) {
        addSelectField(new SelectField(new ColumnField(columnName, tableName), alias));
        return this;
    }

    public Select addSelectField (SelectField field) {
        selectFields.add(field);
        return this;
    }

    public Select addSelectFields (Object... fields) {
        for (Object field : fields) {
            if (field instanceof SelectField) {
                addSelectField((SelectField)field);
            }
            else if (field instanceof String) {
                addSelectField((String)field);
            }
            else {
                addSelectField(field.toString());
            }
        }
        return this;
    }

    public Select addSelectFields (String... rawFields) {
        addSelectFields(rawFields, null);
        return this;
    }

    public Select addSelectFields (String[] fieldColumnNames, String fieldsFormat) {
        addSelectFields(null, fieldColumnNames, fieldsFormat);
        return this;
    }

    public Select addSelectFields (String fieldsTableName, String[] fieldColumnNames, String fieldsFormat) {
        for (String fieldName : fieldColumnNames) {
            String fieldAlias = null;
            if (fieldsFormat != null) {
                fieldAlias = fieldsFormat.replaceAll("%s", fieldName);
            }
            addSelectField(fieldsTableName, fieldName, fieldAlias);
        }
        return this;
    }

    public ConditionGroupConnector getWhereConnector() {
        return whereConditionGroup.getConnector();
    }

    public Select setWhereConnector(ConditionGroupConnector connector) {
        whereConditionGroup.setConnector(connector);
        return this;
    }

    public Select clearWhere() {
        whereConditionGroup.clearConditions();
        return this;
    }

    public ConditionGroup getWhere() {
        return whereConditionGroup;
    }

    public Select addWhere(String rawField, Object value) {
        whereConditionGroup.addCondition(rawField, value);
        return this;
    }

    public Select addWhere(String rawField, Operator operator, Object value) {
        whereConditionGroup.addCondition(rawField, operator, value);
        return this;
    }

    public Select addWhere(Field field, Object value) {
        whereConditionGroup.addCondition(field, value);
        return this;
    }

    public Select addWhere(Field field, Operator operator, Object value) {
        whereConditionGroup.addCondition(field, operator, value);
        return this;
    }

    public Select addWhere (String rawWhereCondition) {
        whereConditionGroup.addCondition(rawWhereCondition);
        return this;
    }

    public Select addWhere (Condition whereCondition) {
        whereConditionGroup.addCondition(whereCondition);
        return this;
    }

    public ConditionGroupConnector getHavingConnector() {
        return havingConditionGroup.getConnector();
    }

    public Select setHavingConnector(ConditionGroupConnector connector) {
        havingConditionGroup.setConnector(connector);
        return this;
    }

    public Select clearHaving() {
        havingConditionGroup.clearConditions();
        return this;
    }

    public ConditionGroup getHaving() {
        return havingConditionGroup;
    }

    public Select addHaving(String rawField, Object value) {
        havingConditionGroup.addCondition(rawField, value);
        return this;
    }

    public Select addHaving(String rawField, Operator operator, Object value) {
        havingConditionGroup.addCondition(rawField, operator, value);
        return this;
    }

    public Select addHaving(Field field, Object value) {
        havingConditionGroup.addCondition(field, value);
        return this;
    }

    public Select addHaving(Field field, Operator operator, Object value) {
        havingConditionGroup.addCondition(field, operator, value);
        return this;
    }

    public Select addHaving (String rawHavingCondition) {
        havingConditionGroup.addCondition(rawHavingCondition);
        return this;
    }

    public Select addHaving (Condition havingCondition) {
        havingConditionGroup.addCondition(havingCondition);
        return this;
    }

    public Select clearOrderByFields () {
        orderByFields.clear();
        return this;
    }

    public List<OrderByField> getOrderByFields() {
        return orderByFields;
    }

    public Select addOrderByField (String rawField) {
        orderByFields.add(new OrderByField(rawField));
        return this;
    }

    public Select addOrderByField (OrderByField field) {
        orderByFields.add(field);
        return this;
    }

    public Select addOrderByField (String rawField, OrderByField.Direction direction) {
        orderByFields.add(new OrderByField(rawField, direction));
        return this;
    }

    public Select clearGroupByFields () {
        groupByFields.clear();
        return this;
    }

    public List<Field> getGroupByFields() {
        return groupByFields;
    }

    public Select addGroupByField (String rawField) {
        addGroupByField(new RawField(rawField));
        return this;
    }

    public Select addGroupByField (Field field) {
        groupByFields.add(field);
        return this;
    }

    public Select clearJoins () {
        joins.clear();
        return this;
    }

    public List<Join> getJoins() {
        return joins;
    }

    public Select addJoin(Join join) {
        joins.add(join);
        return this;
    }

    public Select addJoin(String tableName, String rawLeftField, String rawRightField) {
        joins.add(new Join(tableName, rawLeftField, rawRightField));
        return this;
    }

    public Select addJoin(String tableName, Field leftField, Field rightField) {
        joins.add(new Join(tableName, leftField, rightField));
        return this;
    }

    public Select addJoin(String tableName, JoinType joinType, String rawLeftField, String rawRightField) {
        joins.add(new Join(tableName, joinType, rawLeftField, rawRightField));
        return this;
    }

    public Select addJoin(String tableName, JoinType joinType, Field leftField, Field rightField) {
        joins.add(new Join(tableName, joinType, leftField, rightField));
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public Select setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public Integer getOffset() {
        return offset;
    }

    public Select setOffset(Integer offset) {
        this.offset = offset;
        return this;
    }
}
