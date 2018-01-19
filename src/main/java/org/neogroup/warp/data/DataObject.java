package org.neogroup.warp.data;

import org.neogroup.warp.data.conditions.*;
import org.neogroup.warp.data.joins.Join;
import org.neogroup.warp.data.joins.JoinType;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.*;

public class DataObject extends DataItem {

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
        public static final String ASSIGNATION = "=";

        public static final String SELECT = "SELECT";
        public static final String INSERT_INTO = "INSERT INTO";
        public static final String VALUES = "VALUES";
        public static final String UPDATE = "UPDATE";
        public static final String SET = "SET";
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
    private ResultSet resultSet;
    private ResultSetMetaData resultSetMetaData;
    private String tableName;
    private final List<SelectField> selectFields;
    private final ConditionGroup whereConditionGroup;
    private final ConditionGroup havingConditionGroup;
    private final List<OrderByField> orderByFields;
    private final List<String> groupByFields;
    private final List<Join> joins;
    private Integer limit;
    private Integer offset;

    public DataObject(DataConnection connection, String tableName) {

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

    public DataObject clearSelectFields() {
        selectFields.clear();
        return this;
    }

    public List<SelectField> getSelectFields() {
        return selectFields;
    }

    public DataObject addSelectField (String rawField) {
        addSelectField(rawField, null);
        return this;
    }

    public DataObject addSelectField (String rawField, String alias) {
        addSelectField(new SelectField(rawField, alias));
        return this;
    }

    public DataObject addSelectField (String tableName, String columnName, String alias) {
        addSelectField(new SelectField(tableName + SQL.SCOPE_SEPARATOR + columnName, alias));
        return this;
    }

    public DataObject addSelectField (SelectField field) {
        selectFields.add(field);
        return this;
    }

    public DataObject addSelectFields (Object... fields) {
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

    public DataObject addSelectFields (String... rawFields) {
        addSelectFields(rawFields, null);
        return this;
    }

    public DataObject addSelectFields (String[] fieldColumnNames, String fieldsFormat) {
        addSelectFields(null, fieldColumnNames, fieldsFormat);
        return this;
    }

    public DataObject addSelectFields (String fieldsTableName, String[] fieldColumnNames, String fieldsFormat) {
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

    public DataObject setWhereConnector(ConditionGroupConnector connector) {
        whereConditionGroup.setConnector(connector);
        return this;
    }

    public DataObject clearWhere() {
        whereConditionGroup.clearConditions();
        return this;
    }

    public ConditionGroup getWhere() {
        return whereConditionGroup;
    }

    public DataObject addWhere(String rawField, Object value) {
        whereConditionGroup.addCondition(rawField, value);
        return this;
    }

    public DataObject addWhere(String rawField, Operator operator, Object value) {
        whereConditionGroup.addCondition(rawField, operator, value);
        return this;
    }

    public DataObject addWhere (String rawWhereCondition) {
        whereConditionGroup.addCondition(rawWhereCondition);
        return this;
    }

    public DataObject addWhere (Condition whereCondition) {
        whereConditionGroup.addCondition(whereCondition);
        return this;
    }

    public ConditionGroupConnector getHavingConnector() {
        return havingConditionGroup.getConnector();
    }

    public DataObject setHavingConnector(ConditionGroupConnector connector) {
        havingConditionGroup.setConnector(connector);
        return this;
    }

    public DataObject clearHaving() {
        havingConditionGroup.clearConditions();
        return this;
    }

    public ConditionGroup getHaving() {
        return havingConditionGroup;
    }

    public DataObject addHaving(String rawField, Object value) {
        havingConditionGroup.addCondition(rawField, value);
        return this;
    }

    public DataObject addHaving(String rawField, Operator operator, Object value) {
        havingConditionGroup.addCondition(rawField, operator, value);
        return this;
    }

    public DataObject addHaving (String rawHavingCondition) {
        havingConditionGroup.addCondition(rawHavingCondition);
        return this;
    }

    public DataObject addHaving (Condition havingCondition) {
        havingConditionGroup.addCondition(havingCondition);
        return this;
    }

    public DataObject clearOrderByFields () {
        orderByFields.clear();
        return this;
    }

    public List<OrderByField> getOrderByFields() {
        return orderByFields;
    }

    public DataObject addOrderByField (String rawField) {
        orderByFields.add(new OrderByField(rawField));
        return this;
    }

    public DataObject addOrderByField (OrderByField field) {
        orderByFields.add(field);
        return this;
    }

    public DataObject addOrderByField (String rawField, OrderByField.Direction direction) {
        orderByFields.add(new OrderByField(rawField, direction));
        return this;
    }

    public DataObject clearGroupByFields () {
        groupByFields.clear();
        return this;
    }

    public List<String> getGroupByFields() {
        return groupByFields;
    }

    public DataObject addGroupByField (String field) {
        groupByFields.add(field);
        return this;
    }

    public DataObject clearJoins () {
        joins.clear();
        return this;
    }

    public List<Join> getJoins() {
        return joins;
    }

    public DataObject addJoin(Join join) {
        joins.add(join);
        return this;
    }

    public DataObject addJoin(String tableName, String rawLeftField, String rawRightField) {
        joins.add(new Join(tableName, rawLeftField, rawRightField));
        return this;
    }

    public DataObject addJoin(String tableName, JoinType joinType, String rawLeftField, String rawRightField) {
        joins.add(new Join(tableName, joinType, rawLeftField, rawRightField));
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public DataObject setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public Integer getOffset() {
        return offset;
    }

    public DataObject setOffset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public boolean insert() {
        try {
            List<Object> parameters = new ArrayList<>();
            StringBuilder sql = new StringBuilder();
            buildInsertSQL(sql, parameters);
            PreparedStatement statement = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            int parameterIndex = 1;
            for (Object parameter : parameters) {
                statement.setObject(parameterIndex++, parameter);
            }
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    ResultSetMetaData generatedKeysMetaData = generatedKeys.getMetaData();
                    while (generatedKeys.next()) {
                        for (int column = 1; column <= generatedKeysMetaData.getColumnCount(); column++) {
                            String columnName = generatedKeysMetaData.getColumnName(column);
                            setField(columnName, generatedKeys.getObject(column));
                        }
                    }
                }
            }

            return affectedRows > 0;
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    public int update() {
        try {
            List<Object> parameters = new ArrayList<>();
            StringBuilder sql = new StringBuilder();
            buildUpdateSQL(sql, parameters);
            PreparedStatement statement = connection.prepareStatement(sql.toString());
            int parameterIndex = 1;
            for (Object parameter : parameters) {
                statement.setObject(parameterIndex++, parameter);
            }
            return statement.executeUpdate();
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    public DataObject find() {
        find(false);
        return this;
    }

    public boolean find(boolean autoFetch) {
        try {
            List<Object> parameters = new ArrayList<>();
            StringBuilder sql = new StringBuilder();
            buildSelectSQL(sql, parameters);
            PreparedStatement statement = connection.prepareStatement(sql.toString());
            if (parameters.size() > 0) {
                int parameterIndex = 1;
                for (Object parameter : parameters) {
                    statement.setObject(parameterIndex++, parameter);
                }
            }

            this.resultSet = statement.executeQuery();
            this.resultSetMetaData = this.resultSet.getMetaData();
            boolean fetched = false;
            if (autoFetch) {
                fetched = fetch();
            }
            return fetched;
        }
        catch (Exception ex) {
            throw new DataException (ex);
        }
    }

    public boolean fetch() {

        try {
            clearFields();
            boolean fetched = resultSet.next();
            if (fetched) {
                for (int column = 1; column <= resultSetMetaData.getColumnCount(); column++) {
                    String columnName = resultSetMetaData.getColumnName(column);
                    Object value = resultSet.getObject(column);
                    setField(columnName, value);
                }
            }
            return fetched;
        }
        catch (Exception ex) {
            throw new DataException (ex);
        }
    }

    public DataItem fetchObject () {

        try {
            DataItem result = null;
            if (resultSet.next()) {
                result = new DataItem();
                for (int column = 1; column <= resultSetMetaData.getColumnCount(); column++) {
                    String columnName = resultSetMetaData.getColumnName(column);
                    Object value = resultSet.getObject(column);
                    result.setField(columnName, value);
                }
            }
            return result;
        }
        catch (Exception ex) {
            throw new DataException (ex);
        }
    }

    public <T> T fetchObject(Class<T> resultType) {

        try {
            T result = null;
            if (resultSet.next()) {
                result = resultType.getConstructor().newInstance();
                for (int column = 1; column <= resultSetMetaData.getColumnCount(); column++) {
                    String columnName = resultSetMetaData.getColumnName(column);
                    Object value = resultSet.getObject(column);
                    Field property = resultType.getField(columnName);
                    if (property != null) {
                        property.setAccessible(true);
                        property.set(result, value);
                    }
                }
            }
            return result;
        }
        catch (Exception ex) {
            throw new DataException (ex);
        }
    }

    public List<DataItem> findAll () {
        find();
        DataItem item = null;
        List<DataItem> results = new ArrayList<>();
        while ((item = fetchObject()) != null) {
            results.add(item);
        }
        return results;
    }

    public <T> List<T> findAll (Class<T> resultType) {
        find();
        T item = null;
        List<T> results = new ArrayList<>();
        while ((item = fetchObject(resultType)) != null) {
            results.add(item);
        }
        return results;
    }

    protected void buildInsertSQL(StringBuilder sql, List<Object> parameters) {

        sql.append(SQL.INSERT_INTO);
        sql.append(SQL.SEPARATOR);
        sql.append(getTableName());

        sql.append(SQL.SEPARATOR);
        sql.append(SQL.GROUP_BEGIN);
        Iterator<String> columnsIterator = getFields().keySet().iterator();
        while (columnsIterator.hasNext()) {
            sql.append(columnsIterator.next());
            if (columnsIterator.hasNext()) {
                sql.append(SQL.FIELDS_SEPARATOR);
                sql.append(SQL.SEPARATOR);
            }
        }
        sql.append(SQL.GROUP_END);
        sql.append(SQL.SEPARATOR);
        sql.append(SQL.VALUES);
        sql.append(SQL.SEPARATOR);
        sql.append(SQL.GROUP_BEGIN);
        Iterator valuesIterator = getFields().values().iterator();
        while (valuesIterator.hasNext()) {
            parameters.add(valuesIterator.next());
            sql.append(SQL.PARAMETER);
            if (valuesIterator.hasNext()) {
                sql.append(SQL.FIELDS_SEPARATOR);
                sql.append(SQL.SEPARATOR);
            }
        }
        sql.append(SQL.GROUP_END);
    }

    protected void buildUpdateSQL(StringBuilder sql, List<Object> parameters) {

        sql.append(SQL.UPDATE);
        sql.append(SQL.SEPARATOR);
        sql.append(getTableName());

        sql.append(SQL.SEPARATOR);
        sql.append(SQL.SET);
        sql.append(SQL.SEPARATOR);
        Iterator<Map.Entry<String, Object>> iterator = getFields().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            parameters.add(entry.getValue());
            sql.append(entry.getKey());
            sql.append(SQL.SEPARATOR);
            sql.append(SQL.ASSIGNATION);
            sql.append(SQL.SEPARATOR);
            sql.append(SQL.PARAMETER);
            if (iterator.hasNext()) {
                sql.append(SQL.FIELDS_SEPARATOR);
                sql.append(SQL.SEPARATOR);
            }
        }

        if (!getWhere().getConditions().isEmpty()) {
            sql.append(SQL.SEPARATOR);
            sql.append(SQL.WHERE);
            sql.append(SQL.SEPARATOR);
            buildConditionSQL(getWhere(), sql, parameters);
        }
    }

    protected void buildSelectSQL(StringBuilder sql, List<Object> parameters) {

        sql.append(SQL.SELECT);

        if (getSelectFields().isEmpty()) {
            sql.append(SQL.SEPARATOR);
            sql.append(SQL.WHILDCARD);
        } else {
            Iterator<SelectField> selectFieldsIterator = getSelectFields().iterator();
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
        sql.append(getTableName());

        if (!getJoins().isEmpty()) {
            sql.append(SQL.SEPARATOR);
            for (Join join : getJoins()) {
                buildJoinSQL(join, sql, parameters);
            }
        }

        if (!getWhere().getConditions().isEmpty()) {
            sql.append(SQL.SEPARATOR);
            sql.append(SQL.WHERE);
            sql.append(SQL.SEPARATOR);
            buildConditionSQL(getWhere(), sql, parameters);
        }

        if (!getGroupByFields().isEmpty()) {
            sql.append(SQL.SEPARATOR);
            sql.append(SQL.GROUP_BY);
            Iterator<String> groupByFieldsIterator = getGroupByFields().iterator();
            while (groupByFieldsIterator.hasNext()) {
                sql.append(groupByFieldsIterator.next());
                if (groupByFieldsIterator.hasNext()) {
                    sql.append(SQL.FIELDS_SEPARATOR);
                    sql.append(SQL.SEPARATOR);
                }
            }
        }

        if (!getOrderByFields().isEmpty()) {
            sql.append(SQL.SEPARATOR);
            sql.append(SQL.ORDER_BY);
            Iterator<OrderByField> orderByFieldsIterator = getOrderByFields().iterator();
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

        if (!getHaving().getConditions().isEmpty()) {
            sql.append(SQL.SEPARATOR);
            sql.append(SQL.HAVING);
            sql.append(SQL.SEPARATOR);
            buildConditionSQL(getHaving(), sql, parameters);
        }

        if (getOffset() != null) {
            sql.append(SQL.SEPARATOR);
            sql.append(SQL.OFFSET);
            sql.append(SQL.SEPARATOR);
            sql.append(getOffset());
        }

        if (getLimit() != null) {
            sql.append(SQL.SEPARATOR);
            sql.append(SQL.LIMIT);
            sql.append(SQL.SEPARATOR);
            sql.append(getLimit());
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
            else if (value instanceof DataObject) {
                sql.append(SQL.GROUP_BEGIN);
                ((DataObject)value).buildSelectSQL(sql, parameters);
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
