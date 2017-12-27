package org.neogroup.warp.data;

import org.neogroup.warp.data.query.Select;
import org.neogroup.warp.data.query.Select.SelectField;
import org.neogroup.warp.data.query.conditions.*;
import org.neogroup.warp.data.query.fields.ColumnField;
import org.neogroup.warp.data.query.fields.Field;
import org.neogroup.warp.data.query.fields.RawField;
import org.neogroup.warp.data.query.joins.Join;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class DataConnection {

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
        public static final String WHERE = "WHERE";
        public static final String JOIN = "JOIN";
        public static final String INNER_JOIN = "INNER JOIN";
        public static final String LEFT_JOIN = "LEFT JOIN";
        public static final String RIGHT_JOIN = "RIGHT JOIN";
        public static final String OUTER_JOIN = "OUTER JOIN";
    }

    public abstract Connection getConnection();

    public List<DataObject> executeQuery (String sql, Object... parameters) {

        try (Connection connection = getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            if (parameters.length > 0) {
                int parameterIndex = 1;
                for (Object parameter : parameters) {
                    statement.setObject(parameterIndex++, parameter);
                }
            }

            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            List<DataObject> results = new ArrayList<>();
            while (resultSet.next()) {
                DataObject result = new DataObject();
                for (int column = 1; column <= columnCount; column++) {
                    String columnName = resultSetMetaData.getColumnName(column);
                    Object value = resultSet.getObject(column);
                    result.set(columnName, value);
                }
                results.add(result);
            }
            return results;
        }
        catch (SQLException exception) {
            throw new DataException(exception);
        }
    }

    public <T extends Object> List<T> executeQuery (Class<T> resultType, String sql, Object... parameters) {

        try (Connection connection = getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            if (parameters.length > 0) {
                int parameterIndex = 1;
                for (Object parameter : parameters) {
                    statement.setObject(parameterIndex++, parameter);
                }
            }

            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            List<T> results = new ArrayList<>();
            while (resultSet.next()) {
                T result = resultType.getConstructor().newInstance();
                for (int column = 1; column <= columnCount; column++) {
                    String columnName = resultSetMetaData.getColumnName(column);
                    Object value = resultSet.getObject(column);
                    java.lang.reflect.Field property = resultType.getField(columnName);
                    if (property != null) {
                        property.setAccessible(true);
                        property.set(result, value);
                    }
                }
                results.add(result);
            }
            return results;
        }
        catch (Exception exception) {
            throw new DataException(exception);
        }
    }

    public List<DataObject> executeQuery (Select selectQuery) {

        StringBuilder sql = new StringBuilder();
        List<Object> parameters = new ArrayList<>();
        buildSelectSQL(selectQuery, sql, parameters);
        return executeQuery(sql.toString(), parameters.toArray(new Object[0]));
    }

    public <T extends Object> List<T> executeQuery (Class<T> resultType, Select selectQuery) {

        StringBuilder sql = new StringBuilder();
        List<Object> parameters = new ArrayList<>();
        buildSelectSQL(selectQuery, sql, parameters);
        return executeQuery(resultType, sql.toString(), parameters.toArray(new Object[0]));
    }

    public int executeUpdate (String sql, Object... parameters) {

        try (Connection connection = getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql.toString());

            int parameterIndex = 1;
            for (Object parameter : parameters) {
                statement.setObject(parameterIndex++, parameter);
            }

            return statement.executeUpdate();
        }
        catch (SQLException exception) {
            throw new DataException(exception);
        }
    }

    protected void buildSelectSQL(Select select, StringBuilder sql, List<Object> parameters) {

        sql.append(SQL.SELECT);

        if (select.getSelectFields().isEmpty()) {
            sql.append(SQL.SEPARATOR);
            sql.append(SQL.WHILDCARD);
        } else {
            Iterator<SelectField> selectFieldsIterator = select.getSelectFields().iterator();
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
        sql.append(select.getTableName());

        if (!select.getJoins().isEmpty()) {
            sql.append(SQL.SEPARATOR);
            for (Join join : select.getJoins()) {
                buildJoinSQL(join, sql, parameters);
            }
        }

        if (!select.getWhere().getConditions().isEmpty()) {
            sql.append(SQL.SEPARATOR);
            sql.append(SQL.WHERE);
            sql.append(SQL.SEPARATOR);
            buildConditionSQL(select.getWhere(), sql, parameters);
        }
    }

    protected void buildSelectFieldSQL(SelectField selectField, StringBuilder sql, List<Object> parameters) {

        buildFieldSQL(selectField.getField(), sql, parameters);
        if (selectField.getAlias() != null) {
            sql.append(SQL.SEPARATOR);
            sql.append(SQL.AS);
            sql.append(SQL.SEPARATOR);
            sql.append(selectField.getAlias());
        }
    }

    protected void buildFieldSQL(Field field, StringBuilder sql, List<Object> parameters) {

        if (field instanceof RawField) {
            sql.append(((RawField) field).getValue());
        }
        else if (field instanceof ColumnField) {
            ColumnField columnField = (ColumnField)field;
            if (columnField.getTableName() != null) {
                sql.append(columnField.getTableName());
                sql.append(SQL.SCOPE_SEPARATOR);
            }
            sql.append(columnField.getColumnName());
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
        else if (condition instanceof OperationCondition) {
            buildOperationConditionSQL((OperationCondition)condition, sql, parameters);
        }
    }

    protected void buildOperationConditionSQL(OperationCondition operationCondition, StringBuilder sql, List<Object> parameters) {

        buildOperandSQL(operationCondition.getOperandA(), operationCondition.getOperator(), sql, parameters);
        sql.append(SQL.SEPARATOR);
        switch (operationCondition.getOperator()) {
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
                break;
            case NOT_CONTAINS:
                sql.append("NOT LIKE");
                break;
            case IN:
                sql.append("IN");
                break;
            case NOT_IN:
                sql.append("NOT IN");
                break;
        }
        sql.append(SQL.SEPARATOR);
        buildOperandSQL(operationCondition.getOperandA(), operationCondition.getOperator(), sql, parameters);
    }

    protected void buildOperandSQL(Object operand, Operator operator, StringBuilder sql, List<Object> parameters) {

        if (operand instanceof Field) {
            buildFieldSQL((Field)operand, sql, parameters);
        }
        else if (operand instanceof Collection) {
            sql.append(SQL.GROUP_BEGIN);
            Iterator iterator = ((Collection)operand).iterator();
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
        else if (operand instanceof Select) {
            sql.append(SQL.GROUP_BEGIN);
            buildSelectSQL((Select)operand, sql, parameters);
            sql.append(SQL.GROUP_END);
        }
        else if (operand instanceof String) {
            sql.append(SQL.PARAMETER);
            if (operator.equals(Operator.CONTAINS) || operator.equals(Operator.NOT_CONTAINS)) {
                parameters.add(SQL.CONTAINS_WILDCARD + operand + SQL.CONTAINS_WILDCARD);
            }
            else {
                parameters.add(operand);
            }
        }
        else {
            sql.append(SQL.PARAMETER);
            parameters.add(operand);
        }
    }

    protected void buildConditionGroupSQL(ConditionGroup conditionGroup, StringBuilder sql, List<Object> parameters) {

        List<Condition> conditions = conditionGroup.getConditions();
        Iterator<Condition> conditionsIterator = conditions.iterator();
        while (conditionsIterator.hasNext()) {
            Condition childrenCondition = conditionsIterator.next();
            if (conditionsIterator.hasNext()) {
                buildConditionSQL(childrenCondition, sql, parameters);
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
