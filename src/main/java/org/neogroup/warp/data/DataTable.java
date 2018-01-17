package org.neogroup.warp.data;

import org.neogroup.warp.data.conditions.*;
import org.neogroup.warp.data.joins.Join;
import org.neogroup.warp.data.joins.JoinType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class DataTable extends DataObject {

    public class SQL {
        public static final String WHILDCARD = "*";
        public static final String SEPARATOR = " ";
        public static final String SCOPE_SEPARATOR = ".";
        public static final String FIELDS_SEPARATOR = ",";
        public static final String ARRAY_SEPARATOR = ",";
        public static final String GROUP_BEGIN = "(";
        public static final String GROUP_END = ")";
        public static final String PARAMETER = "?";
        public static final String CONTAINS_WILDCARD = "%";

        public static final String SELECT = "SELECT";
        public static final String AS = "AS";
        public static final String FROM = "FROM";
        public static final String AND = "AND";
        public static final String OR = "OR";
        public static final String ON = "ON";
        public static final String ASC = "ASC";
        public static final String DESC = "DESC";
        public static final String WHERE = "WHERE";
        public static final String HAVING = "HAVING";
        public static final String LIMIT = "LIMIT";
        public static final String OFFSET = "OFFSET";
        public static final String GROUP_BY = "GROUP BY";
        public static final String ORDER_BY = "ORDER BY";
        public static final String JOIN = "JOIN";
        public static final String INNER_JOIN = "INNER JOIN";
        public static final String LEFT_JOIN = "LEFT JOIN";
        public static final String RIGHT_JOIN = "RIGHT JOIN";
        public static final String OUTER_JOIN = "OUTER JOIN";
    }

    private final DataConnection connection;
    private final String tableName;
    private final List<SelectField> selectFields;
    private final ConditionGroup whereConditionGroup;
    private final ConditionGroup havingConditionGroup;
    private final List<OrderByField> orderByFields;
    private final List<String> groupByFields;
    private final List<Join> joins;
    private Integer limit;
    private Integer offset;

    public DataTable(DataConnection connection, String tableName) {

        this.connection = connection;
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

    public DataTable clearSelectFields() {
        selectFields.clear();
        return this;
    }

    public List<SelectField> getSelectFields() {
        return selectFields;
    }

    public DataTable addSelectField (String rawField) {
        addSelectField(rawField, null);
        return this;
    }

    public DataTable addSelectField (String rawField, String alias) {
        addSelectField(new SelectField(rawField, alias));
        return this;
    }

    public DataTable addSelectField (String tableName, String columnName, String alias) {
        addSelectField(new SelectField(tableName + SQL.SCOPE_SEPARATOR + columnName, alias));
        return this;
    }

    public DataTable addSelectField (SelectField field) {
        selectFields.add(field);
        return this;
    }

    public DataTable addSelectFields (Object... fields) {
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

    public DataTable addSelectFields (String... rawFields) {
        addSelectFields(rawFields, null);
        return this;
    }

    public DataTable addSelectFields (String[] fieldColumnNames, String fieldsFormat) {
        addSelectFields(null, fieldColumnNames, fieldsFormat);
        return this;
    }

    public DataTable addSelectFields (String fieldsTableName, String[] fieldColumnNames, String fieldsFormat) {
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

    public DataTable setWhereConnector(ConditionGroupConnector connector) {
        whereConditionGroup.setConnector(connector);
        return this;
    }

    public DataTable clearWhere() {
        whereConditionGroup.clearConditions();
        return this;
    }

    public ConditionGroup getWhere() {
        return whereConditionGroup;
    }

    public DataTable addWhere(String rawField, Object value) {
        whereConditionGroup.addCondition(rawField, value);
        return this;
    }

    public DataTable addWhere(String rawField, Operator operator, Object value) {
        whereConditionGroup.addCondition(rawField, operator, value);
        return this;
    }

    public DataTable addWhere (String rawWhereCondition) {
        whereConditionGroup.addCondition(rawWhereCondition);
        return this;
    }

    public DataTable addWhere (Condition whereCondition) {
        whereConditionGroup.addCondition(whereCondition);
        return this;
    }

    public ConditionGroupConnector getHavingConnector() {
        return havingConditionGroup.getConnector();
    }

    public DataTable setHavingConnector(ConditionGroupConnector connector) {
        havingConditionGroup.setConnector(connector);
        return this;
    }

    public DataTable clearHaving() {
        havingConditionGroup.clearConditions();
        return this;
    }

    public ConditionGroup getHaving() {
        return havingConditionGroup;
    }

    public DataTable addHaving(String rawField, Object value) {
        havingConditionGroup.addCondition(rawField, value);
        return this;
    }

    public DataTable addHaving(String rawField, Operator operator, Object value) {
        havingConditionGroup.addCondition(rawField, operator, value);
        return this;
    }

    public DataTable addHaving (String rawHavingCondition) {
        havingConditionGroup.addCondition(rawHavingCondition);
        return this;
    }

    public DataTable addHaving (Condition havingCondition) {
        havingConditionGroup.addCondition(havingCondition);
        return this;
    }

    public DataTable clearOrderByFields () {
        orderByFields.clear();
        return this;
    }

    public List<OrderByField> getOrderByFields() {
        return orderByFields;
    }

    public DataTable addOrderByField (String rawField) {
        orderByFields.add(new OrderByField(rawField));
        return this;
    }

    public DataTable addOrderByField (OrderByField field) {
        orderByFields.add(field);
        return this;
    }

    public DataTable addOrderByField (String rawField, OrderByField.Direction direction) {
        orderByFields.add(new OrderByField(rawField, direction));
        return this;
    }

    public DataTable clearGroupByFields () {
        groupByFields.clear();
        return this;
    }

    public List<String> getGroupByFields() {
        return groupByFields;
    }

    public DataTable addGroupByField (String field) {
        groupByFields.add(field);
        return this;
    }

    public DataTable clearJoins () {
        joins.clear();
        return this;
    }

    public List<Join> getJoins() {
        return joins;
    }

    public DataTable addJoin(Join join) {
        joins.add(join);
        return this;
    }

    public DataTable addJoin(String tableName, String rawLeftField, String rawRightField) {
        joins.add(new Join(tableName, rawLeftField, rawRightField));
        return this;
    }

    public DataTable addJoin(String tableName, JoinType joinType, String rawLeftField, String rawRightField) {
        joins.add(new Join(tableName, joinType, rawLeftField, rawRightField));
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public DataTable setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public Integer getOffset() {
        return offset;
    }

    public DataTable setOffset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public List<DataObject> findAll () {
        List<Object> parameters = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        buildSelectSQL(this, sql, parameters);
        return connection.executeQuery(sql.toString(), parameters.toArray(new Object[0]));
    }

    public <T> List<T> findAll (Class<T> resultType) {
        List<Object> parameters = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        buildSelectSQL(this, sql, parameters);
        return connection.executeQuery(resultType, sql.toString(), parameters.toArray(new Object[0]));
    }

    protected void buildSelectSQL(DataTable dataTable, StringBuilder sql, List<Object> parameters) {

        sql.append(SQL.SELECT);

        if (dataTable.getSelectFields().isEmpty()) {
            sql.append(SQL.SEPARATOR);
            sql.append(SQL.WHILDCARD);
        } else {
            Iterator<SelectField> selectFieldsIterator = dataTable.getSelectFields().iterator();
            while (selectFieldsIterator.hasNext()) {
                SelectField selectField = selectFieldsIterator.next();
                sql.append(SQL.SEPARATOR);
                buildSelectFieldSQL(selectField, sql, parameters);
                if (selectFieldsIterator.hasNext()) {
                    sql.append(SQL.FIELDS_SEPARATOR);
                }
            }
        }

        sql.append(SQL.SEPARATOR);
        sql.append(SQL.FROM);
        sql.append(SQL.SEPARATOR);
        sql.append(dataTable.getTableName());

        if (!dataTable.getJoins().isEmpty()) {
            sql.append(SQL.SEPARATOR);
            for (Join join : dataTable.getJoins()) {
                buildJoinSQL(join, sql, parameters);
            }
        }

        if (!dataTable.getWhere().getConditions().isEmpty()) {
            sql.append(SQL.SEPARATOR);
            sql.append(SQL.WHERE);
            sql.append(SQL.SEPARATOR);
            buildConditionSQL(dataTable.getWhere(), sql, parameters);
        }

        if (!dataTable.getGroupByFields().isEmpty()) {
            sql.append(SQL.SEPARATOR);
            sql.append(SQL.GROUP_BY);
            Iterator<String> groupByFieldsIterator = dataTable.getGroupByFields().iterator();
            while (groupByFieldsIterator.hasNext()) {
                sql.append(groupByFieldsIterator.next());
                if (groupByFieldsIterator.hasNext()) {
                    sql.append(SQL.FIELDS_SEPARATOR);
                    sql.append(SQL.SEPARATOR);
                }
            }
        }

        if (!dataTable.getOrderByFields().isEmpty()) {
            sql.append(SQL.SEPARATOR);
            sql.append(SQL.ORDER_BY);
            Iterator<OrderByField> orderByFieldsIterator = dataTable.getOrderByFields().iterator();
            while (orderByFieldsIterator.hasNext()) {
                OrderByField orderByField = orderByFieldsIterator.next();
                sql.append(orderByField.getField());
                sql.append(SQL.SEPARATOR);
                switch (orderByField.getDirection()) {
                    case ASC:
                        sql.append(SQL.ASC);
                        break;
                    case DESC:
                        sql.append(SQL.DESC);
                        break;
                }
                if (orderByFieldsIterator.hasNext()) {
                    sql.append(SQL.FIELDS_SEPARATOR);
                    sql.append(SQL.SEPARATOR);
                }
            }
        }

        if (!dataTable.getHaving().getConditions().isEmpty()) {
            sql.append(SQL.SEPARATOR);
            sql.append(SQL.HAVING);
            sql.append(SQL.SEPARATOR);
            buildConditionSQL(dataTable.getHaving(), sql, parameters);
        }

        if (dataTable.getOffset() != null) {
            sql.append(SQL.SEPARATOR);
            sql.append(SQL.OFFSET);
            sql.append(SQL.SEPARATOR);
            sql.append(dataTable.getOffset());
        }

        if (dataTable.getLimit() != null) {
            sql.append(SQL.SEPARATOR);
            sql.append(SQL.LIMIT);
            sql.append(SQL.SEPARATOR);
            sql.append(dataTable.getLimit());
        }
    }

    protected void buildSelectFieldSQL(SelectField selectField, StringBuilder sql, List<Object> parameters) {

        sql.append(selectField.getField());
        if (selectField.getAlias() != null) {
            sql.append(SQL.SEPARATOR);
            sql.append(SQL.AS);
            sql.append(SQL.SEPARATOR);
            sql.append(selectField.getAlias());
        }
    }

    protected void buildJoinSQL(Join join, StringBuilder sql, List<Object> parameters) {

        switch (join.getJoinType()) {
            case JOIN:
                sql.append(SQL.JOIN);
                break;
            case INNER_JOIN:
                sql.append(SQL.INNER_JOIN);
                break;
            case LEFT_JOIN:
                sql.append(SQL.LEFT_JOIN);
                break;
            case RIGHT_JOIN:
                sql.append(SQL.RIGHT_JOIN);
                break;
            case OUTER_JOIN:
                sql.append(SQL.OUTER_JOIN);
                break;
        }
        sql.append(SQL.SEPARATOR);
        sql.append(join.getTableName());
        sql.append(SQL.SEPARATOR);
        sql.append(SQL.ON);
        sql.append(SQL.SEPARATOR);
        buildConditionSQL(join.getConditions(), sql, parameters);
    }

    protected void buildConditionSQL(Condition condition, StringBuilder sql, List<Object> parameters) {

        if (condition instanceof RawCondition) {
            sql.append(((RawCondition) condition).getCondition());
        }
        else if (condition instanceof ConditionGroup) {
            buildConditionGroupSQL((ConditionGroup)condition, sql, parameters);
        }
        else if (condition instanceof FieldCondition) {
            buildFieldConditionSQL((FieldCondition)condition, sql, parameters);
        }
    }

    protected void buildFieldConditionSQL(FieldCondition fieldCondition, StringBuilder sql, List<Object> parameters) {

        if (fieldCondition instanceof FieldOperationCondition) {
            FieldOperationCondition fieldOperationCondition = (FieldOperationCondition)fieldCondition;
            sql.append(fieldCondition.getField());
            sql.append(SQL.SEPARATOR);
            Object value = fieldOperationCondition.getValue();
            switch (fieldOperationCondition.getOperator()) {
                case EQUALS:
                    sql.append("=");
                    break;
                case NOT_EQUALS:
                    sql.append("!=");
                    break;
                case GREATER_THAN:
                    sql.append(">");
                    break;
                case GREATER_OR_EQUALS_THAN:
                    sql.append(">=");
                    break;
                case LOWER_THAN:
                    sql.append("<");
                    break;
                case LOWER_OR_EQUALS_THAN:
                    sql.append("<=");
                    break;
                case CONTAINS:
                    sql.append("LIKE");
                    if (value instanceof String) {
                        value = SQL.CONTAINS_WILDCARD + value + SQL.CONTAINS_WILDCARD;
                    }
                    break;
                case NOT_CONTAINS:
                    sql.append("NOT LIKE");
                    if (value instanceof String) {
                        value = SQL.CONTAINS_WILDCARD + value + SQL.CONTAINS_WILDCARD;
                    }
                    break;
                case IN:
                    sql.append("IN");
                    break;
                case NOT_IN:
                    sql.append("NOT IN");
                    break;
            }
            sql.append(SQL.SEPARATOR);

            if (value instanceof RawValue) {
                sql.append(((RawValue) value).getValue());
            }
            else if (value instanceof Collection) {
                sql.append(SQL.GROUP_BEGIN);
                Iterator iterator = ((Collection)value).iterator();
                while (iterator.hasNext()) {
                    Object childElement = iterator.next();
                    sql.append(SQL.PARAMETER);
                    parameters.add(childElement);
                    if (iterator.hasNext()) {
                        sql.append(SQL.ARRAY_SEPARATOR);
                    }
                }
                sql.append(SQL.GROUP_END);
            }
            else if (value instanceof DataTable) {
                sql.append(SQL.GROUP_BEGIN);
                buildSelectSQL((DataTable)value, sql, parameters);
                sql.append(SQL.GROUP_END);
            }
            else {
                sql.append(SQL.PARAMETER);
                parameters.add(value);
            }
        }
    }

    protected void buildConditionGroupSQL(ConditionGroup conditionGroup, StringBuilder sql, List<Object> parameters) {

        List<Condition> conditions = conditionGroup.getConditions();
        Iterator<Condition> conditionsIterator = conditions.iterator();
        while (conditionsIterator.hasNext()) {
            Condition childrenCondition = conditionsIterator.next();
            buildConditionSQL(childrenCondition, sql, parameters);
            if (conditionsIterator.hasNext()) {
                sql.append(SQL.SEPARATOR);
                switch (conditionGroup.getConnector()) {
                    case AND:
                        sql.append(SQL.AND);
                        break;
                    case OR:
                        sql.append(SQL.OR);
                        break;
                }
                sql.append(SQL.SEPARATOR);
            }
        }
    }
}
