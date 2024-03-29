package org.neogroup.warp.data;

import org.neogroup.warp.data.query.*;
import org.neogroup.warp.data.query.builders.QueryBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.neogroup.warp.Warp.getLogger;

public class DataConnection  {

    private final Connection connection;
    private final QueryBuilder queryBuilder;

    protected DataConnection(Connection connection, QueryBuilder queryBuilder) {
        this.connection = connection;
        this.queryBuilder = queryBuilder;
    }

    public void close() {
        try {
            connection.close();
        }
        catch (Exception ex ) {
            throw new RuntimeException(ex);
        }
    }

    public boolean isClosed() {
        try {
            return connection.isClosed();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setReadOnly(boolean readOnly) {
        try {
            connection.setReadOnly(readOnly);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean isReadOnly() {
        try {
            return connection.isReadOnly();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public DataTable getTable(String table) {
        return new DataTable(this, table);
    }

    public Collection<DataObject> query(Query query) {
        List<Object> bindings = new ArrayList<>();
        String sql = queryBuilder.buildQuery(query, bindings);
        return query(sql, bindings);
    }

    public Collection<DataObject> query(String sql) {
        return query(sql, null);
    }

    public Collection<DataObject> query(String sql, List<Object> bindings) {
        try {
            PreparedStatement statement = getStatement(sql, bindings);
            getLogger().info("SQL: " + statement.toString());
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<DataObject> data = new ArrayList<>();
            while (resultSet.next()) {
                DataObject dataObject = Data.object();
                for (int i = 1; i <= columnCount; i++) {
                    dataObject.set(metaData.getColumnLabel(i), resultSet.getObject(i));
                }
                data.add(dataObject);
            }
            return data;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public int execute (Query query) {
        List<Object> bindings = new ArrayList<>();
        String sql = queryBuilder.buildQuery(query, bindings);
        return execute(sql, bindings);
    }

    public int execute (String sql) {
        return execute(sql, null);
    }

    public int execute (String sql, List<Object> bindings) {
        try {
            PreparedStatement statement = getStatement(sql, bindings);
            getLogger().info("SQL: " + statement.toString());
            return statement.executeUpdate();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private PreparedStatement getStatement (String sql, List<Object> bindings) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        if (bindings != null) {
            int bindingIndex = 1;
            for (Object binding : bindings) {
                if (binding instanceof java.util.Date) {
                    statement.setTimestamp(bindingIndex++, new Timestamp(((java.util.Date) binding).getTime()));
                } else {
                    statement.setObject(bindingIndex++, binding);
                }
            }
        }
        return statement;
    }
}