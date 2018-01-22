package org.neogroup.warp.data;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 *
 */
public class DataConnection implements Connection {

    private final Connection connection;

    /**
     *
     * @param connection
     */
    protected DataConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     *
     * @return
     */
    @Override
    public Statement createStatement() {
        try {
            return connection.createStatement();
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param sql
     * @return
     */
    @Override
    public PreparedStatement prepareStatement(String sql) {
        try {
            return new DataStatement(connection.prepareStatement(sql));
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param sql
     * @return
     */
    @Override
    public CallableStatement prepareCall(String sql) {
        try {
            return connection.prepareCall(sql);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param sql
     * @return
     */
    @Override
    public String nativeSQL(String sql) {
        try {
            return connection.nativeSQL(sql);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param autoCommit
     */
    @Override
    public void setAutoCommit(boolean autoCommit) {
        try {
            connection.setAutoCommit(autoCommit);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public boolean getAutoCommit() {
        try {
            return connection.getAutoCommit();
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     */
    @Override
    public void commit() {
        try {
            connection.commit();
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     */
    @Override
    public void rollback() {
        try {
            connection.rollback();
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     */
    @Override
    public void close() {
        try {
            connection.close();
        }
        catch (Exception ex ) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isClosed() {
        try {
            return connection.isClosed();
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public DatabaseMetaData getMetaData() {
        try {
            return connection.getMetaData();
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param readOnly
     */
    @Override
    public void setReadOnly(boolean readOnly) {
        try {
            connection.setReadOnly(readOnly);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isReadOnly() {
        try {
            return connection.isReadOnly();
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param catalog
     */
    @Override
    public void setCatalog(String catalog) {
        try {
            connection.setCatalog(catalog);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String getCatalog() {
        try {
            return connection.getCatalog();
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param level
     */
    @Override
    public void setTransactionIsolation(int level) {
        try {
            connection.setTransactionIsolation(level);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public int getTransactionIsolation() {
        try {
            return connection.getTransactionIsolation();
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public SQLWarning getWarnings() {
        try {
            return connection.getWarnings();
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     */
    @Override
    public void clearWarnings() {
        try {
            connection.clearWarnings();
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param resultSetType
     * @param resultSetConcurrency
     * @return
     */
    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) {
        try {
            return connection.createStatement(resultSetType, resultSetConcurrency);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param sql
     * @param resultSetType
     * @param resultSetConcurrency
     * @return
     */
    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) {
        try {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param sql
     * @param resultSetType
     * @param resultSetConcurrency
     * @return
     */
    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) {
        try {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public Map<String, Class<?>> getTypeMap() {
        try {
            return connection.getTypeMap();
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param map
     */
    @Override
    public void setTypeMap(Map<String, Class<?>> map) {
        try {
            connection.setTypeMap(map);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param holdability
     */
    @Override
    public void setHoldability(int holdability) {
        try {
            connection.setHoldability(holdability);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public int getHoldability() {
        try {
            return connection.getHoldability();
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public Savepoint setSavepoint() {
        try {
            return connection.setSavepoint();
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param name
     * @return
     */
    @Override
    public Savepoint setSavepoint(String name) {
        try {
            return connection.setSavepoint(name);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param savepoint
     */
    @Override
    public void rollback(Savepoint savepoint) {
        try {
            connection.rollback(savepoint);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param savepoint
     */
    @Override
    public void releaseSavepoint(Savepoint savepoint) {
        try {
            connection.releaseSavepoint(savepoint);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param resultSetType
     * @param resultSetConcurrency
     * @param resultSetHoldability
     * @return
     */
    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) {
        try {
            return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param sql
     * @param resultSetType
     * @param resultSetConcurrency
     * @param resultSetHoldability
     * @return
     */
    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) {
        try {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param sql
     * @param resultSetType
     * @param resultSetConcurrency
     * @param resultSetHoldability
     * @return
     */
    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) {
        try {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param sql
     * @param autoGeneratedKeys
     * @return
     */
    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) {
        try {
            return connection.prepareStatement(sql, autoGeneratedKeys);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param sql
     * @param columnIndexes
     * @return
     */
    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) {
        try {
            return connection.prepareStatement(sql, columnIndexes);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param sql
     * @param columnNames
     * @return
     */
    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) {
        try {
            return connection.prepareStatement(sql, columnNames);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public Clob createClob() {
        try {
            return connection.createClob();
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public Blob createBlob() {
        try {
            return connection.createBlob();
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public NClob createNClob() {
        try {
            return connection.createNClob();
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public SQLXML createSQLXML() {
        try {
            return connection.createSQLXML();
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param timeout
     * @return
     */
    @Override
    public boolean isValid(int timeout) {
        try {
            return connection.isValid(timeout);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param name
     * @param value
     */
    @Override
    public void setClientInfo(String name, String value) {
        try {
            connection.setClientInfo(name, value);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param properties
     */
    @Override
    public void setClientInfo(Properties properties) {
        try {
            connection.setClientInfo(properties);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param name
     * @return
     */
    @Override
    public String getClientInfo(String name) {
        try {
            return connection.getClientInfo(name);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public Properties getClientInfo() {
        try {
            return connection.getClientInfo();
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param typeName
     * @param elements
     * @return
     */
    @Override
    public Array createArrayOf(String typeName, Object[] elements) {
        try {
            return connection.createArrayOf(typeName, elements);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param typeName
     * @param attributes
     * @return
     */
    @Override
    public Struct createStruct(String typeName, Object[] attributes) {
        try {
            return connection.createStruct(typeName, attributes);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param schema
     */
    @Override
    public void setSchema(String schema) {
        try {
            connection.setSchema(schema);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String getSchema() {
        try {
            return connection.getSchema();
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param executor
     */
    @Override
    public void abort(Executor executor) {
        try {
            connection.abort(executor);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param executor
     * @param milliseconds
     */
    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) {
        try {
            connection.setNetworkTimeout(executor, milliseconds);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public int getNetworkTimeout() {
        try {
            return connection.getNetworkTimeout();
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param iface
     * @param <T>
     * @return
     */
    @Override
    public <T> T unwrap(Class<T> iface) {
        try {
            return connection.unwrap(iface);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param iface
     * @return
     */
    @Override
    public boolean isWrapperFor(Class<?> iface) {
        try {
            return connection.isWrapperFor(iface);
        }
        catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    /**
     *
     * @param tableName
     * @return
     */
    public DataObject getDataObject(String tableName) {
        return new DataObject(this, tableName);
    }
}
