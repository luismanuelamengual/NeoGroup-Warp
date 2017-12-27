package org.neogroup.warp.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DataConnection {

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
            List<DataObject> dataObjects = new ArrayList<>();
            while (resultSet.next()) {
                DataObject dataObject = new DataObject();
                for (int column = 1; column <= columnCount; column++) {
                    String columnName = resultSetMetaData.getColumnName(column);
                    Object value = resultSet.getObject(column);
                    dataObject.set(columnName, value);
                }
                dataObjects.add(dataObject);
            }
            return dataObjects;
        }
        catch (SQLException exception) {
            throw new DataException(exception);
        }
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
}
