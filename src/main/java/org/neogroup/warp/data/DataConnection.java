package org.neogroup.warp.data;

import org.neogroup.warp.data.query.Select;
import org.neogroup.warp.data.query.Select.SelectField;
import org.neogroup.warp.data.query.fields.ColumnField;
import org.neogroup.warp.data.query.fields.Field;
import org.neogroup.warp.data.query.fields.RawField;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class DataConnection {

    public class SQL {
        public static final String WHILDCARD = "*";
        public static final String SEPARATOR = " ";
        public static final String SCOPE_SEPARATOR = ".";
        public static final String FIELDS_SEPARATOR = ",";

        public static final String SELECT = "SELECT";
        public static final String AS = "AS";
        public static final String FROM = "FROM";
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
        buildSQL(selectQuery, sql, parameters);
        return executeQuery(sql.toString(), parameters.toArray(new Object[0]));
    }

    public <T extends Object> List<T> executeQuery (Class<T> resultType, Select selectQuery) {

        StringBuilder sql = new StringBuilder();
        List<Object> parameters = new ArrayList<>();
        buildSQL(selectQuery, sql, parameters);
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

    protected void buildSQL (Select select, StringBuilder sql, List<Object> parameters) {

        sql.append(SQL.SELECT);

        if (select.getSelectFields().isEmpty()) {
            sql.append(SQL.SEPARATOR);
            sql.append(SQL.WHILDCARD);
        }
        else {
            Iterator<SelectField> selectFieldsIterator = select.getSelectFields().iterator();
            while (selectFieldsIterator.hasNext()) {
                SelectField selectField = selectFieldsIterator.next();
                sql.append(SQL.SEPARATOR);
                buildSQL(selectField, sql, parameters);
                if (selectFieldsIterator.hasNext()) {
                    sql.append(SQL.FIELDS_SEPARATOR);
                }
            }
        }

        sql.append(SQL.SEPARATOR);
        sql.append(SQL.FROM);
        sql.append(SQL.SEPARATOR);
        sql.append(select.getTableName());
    }

    protected void buildSQL (SelectField selectField, StringBuilder sql, List<Object> parameters) {

        buildSQL(selectField.getField(), sql, parameters);
        if (selectField.getAlias() != null) {
            sql.append(SQL.SEPARATOR);
            sql.append(SQL.AS);
            sql.append(SQL.SEPARATOR);
            sql.append(selectField.getAlias());
        }
    }

    protected void buildSQL (Field field, StringBuilder sql, List<Object> parameters) {

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
}
