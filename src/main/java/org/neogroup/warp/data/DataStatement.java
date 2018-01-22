package org.neogroup.warp.data;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;

import static org.neogroup.warp.Warp.getLogger;

/**
 *
 */
public class DataStatement implements PreparedStatement {

    private final PreparedStatement statement;

    /**
     *
     * @param statement
     */
    public DataStatement(PreparedStatement statement) {
        this.statement = statement;
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public ResultSet executeQuery() throws SQLException {
        getLogger().info("Query SQL: " + this.toString());
        return statement.executeQuery();
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public int executeUpdate() throws SQLException {
        getLogger().info("Update SQL: " + this.toString());
        return statement.executeUpdate();
    }

    /**
     *
     * @param parameterIndex
     * @param sqlType
     * @throws SQLException
     */
    @Override
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        statement.setNull(parameterIndex, sqlType);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @throws SQLException
     */
    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        statement.setBoolean(parameterIndex, x);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @throws SQLException
     */
    @Override
    public void setByte(int parameterIndex, byte x) throws SQLException {
        statement.setByte(parameterIndex, x);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @throws SQLException
     */
    @Override
    public void setShort(int parameterIndex, short x) throws SQLException {
        statement.setShort(parameterIndex, x);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @throws SQLException
     */
    @Override
    public void setInt(int parameterIndex, int x) throws SQLException {
        statement.setInt(parameterIndex, x);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @throws SQLException
     */
    @Override
    public void setLong(int parameterIndex, long x) throws SQLException {
        statement.setLong(parameterIndex, x);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @throws SQLException
     */
    @Override
    public void setFloat(int parameterIndex, float x) throws SQLException {
        statement.setFloat(parameterIndex, x);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @throws SQLException
     */
    @Override
    public void setDouble(int parameterIndex, double x) throws SQLException {
        statement.setDouble(parameterIndex, x);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @throws SQLException
     */
    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        statement.setBigDecimal(parameterIndex, x);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @throws SQLException
     */
    @Override
    public void setString(int parameterIndex, String x) throws SQLException {
        statement.setString(parameterIndex, x);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @throws SQLException
     */
    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        statement.setBytes(parameterIndex, x);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @throws SQLException
     */
    @Override
    public void setDate(int parameterIndex, Date x) throws SQLException {
        statement.setDate(parameterIndex, x);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @throws SQLException
     */
    @Override
    public void setTime(int parameterIndex, Time x) throws SQLException {
        statement.setTime(parameterIndex, x);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @throws SQLException
     */
    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        statement.setTimestamp(parameterIndex, x);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @param length
     * @throws SQLException
     */
    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        statement.setAsciiStream(parameterIndex, x, length);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @param length
     * @throws SQLException
     */
    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        statement.setBinaryStream(parameterIndex, x, length);
    }

    /**
     *
     * @throws SQLException
     */
    @Override
    public void clearParameters() throws SQLException {
        statement.clearParameters();
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @param targetSqlType
     * @throws SQLException
     */
    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        statement.setObject(parameterIndex, x, targetSqlType);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @throws SQLException
     */
    @Override
    public void setObject(int parameterIndex, Object x) throws SQLException {
        statement.setObject(parameterIndex, x);
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public boolean execute() throws SQLException {
        return statement.execute();
    }

    /**
     *
     * @throws SQLException
     */
    @Override
    public void addBatch() throws SQLException {
        statement.addBatch();
    }

    /**
     *
     * @param parameterIndex
     * @param reader
     * @param length
     * @throws SQLException
     */
    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        statement.setCharacterStream(parameterIndex, reader, length);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @throws SQLException
     */
    @Override
    public void setRef(int parameterIndex, Ref x) throws SQLException {
        statement.setRef(parameterIndex, x);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @throws SQLException
     */
    @Override
    public void setBlob(int parameterIndex, Blob x) throws SQLException {
        statement.setBlob(parameterIndex, x);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @throws SQLException
     */
    @Override
    public void setClob(int parameterIndex, Clob x) throws SQLException {
        statement.setClob(parameterIndex, x);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @throws SQLException
     */
    @Override
    public void setArray(int parameterIndex, Array x) throws SQLException {
        statement.setArray(parameterIndex, x);
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return statement.getMetaData();
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @param cal
     * @throws SQLException
     */
    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        statement.setDate(parameterIndex, x, cal);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @param cal
     * @throws SQLException
     */
    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        statement.setTime(parameterIndex, x, cal);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @param cal
     * @throws SQLException
     */
    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        statement.setTimestamp(parameterIndex, x, cal);
    }

    /**
     *
     * @param parameterIndex
     * @param sqlType
     * @param typeName
     * @throws SQLException
     */
    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
        statement.setNull(parameterIndex, sqlType, typeName);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @throws SQLException
     */
    @Override
    public void setURL(int parameterIndex, URL x) throws SQLException {
        statement.setURL(parameterIndex, x);
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        return statement.getParameterMetaData();
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @throws SQLException
     */
    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        statement.setRowId(parameterIndex, x);
    }

    /**
     *
     * @param parameterIndex
     * @param value
     * @throws SQLException
     */
    @Override
    public void setNString(int parameterIndex, String value) throws SQLException {
        statement.setNString(parameterIndex, value);
    }

    /**
     *
     * @param parameterIndex
     * @param value
     * @param length
     * @throws SQLException
     */
    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        statement.setNCharacterStream(parameterIndex, value, length);
    }

    /**
     *
     * @param parameterIndex
     * @param value
     * @throws SQLException
     */
    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        statement.setNClob(parameterIndex, value);
    }

    /**
     *
     * @param parameterIndex
     * @param reader
     * @param length
     * @throws SQLException
     */
    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        statement.setClob(parameterIndex, reader, length);
    }

    /**
     *
     * @param parameterIndex
     * @param inputStream
     * @param length
     * @throws SQLException
     */
    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        statement.setBlob(parameterIndex, inputStream, length);
    }

    /**
     *
     * @param parameterIndex
     * @param reader
     * @param length
     * @throws SQLException
     */
    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        statement.setNClob(parameterIndex, reader, length);
    }

    /**
     *
     * @param parameterIndex
     * @param xmlObject
     * @throws SQLException
     */
    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        statement.setSQLXML(parameterIndex, xmlObject);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @param targetSqlType
     * @param scaleOrLength
     * @throws SQLException
     */
    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
        statement.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @param length
     * @throws SQLException
     */
    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        statement.setAsciiStream(parameterIndex, x, length);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @param length
     * @throws SQLException
     */
    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        statement.setBinaryStream(parameterIndex, x, length);
    }

    /**
     *
     * @param parameterIndex
     * @param reader
     * @param length
     * @throws SQLException
     */
    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        statement.setCharacterStream(parameterIndex, reader, length);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @throws SQLException
     */
    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        statement.setAsciiStream(parameterIndex, x);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @throws SQLException
     */
    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        statement.setBinaryStream(parameterIndex, x);
    }

    /**
     *
     * @param parameterIndex
     * @param reader
     * @throws SQLException
     */
    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        statement.setCharacterStream(parameterIndex, reader);
    }

    /**
     *
     * @param parameterIndex
     * @param value
     * @throws SQLException
     */
    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        statement.setNCharacterStream(parameterIndex, value);
    }

    /**
     *
     * @param parameterIndex
     * @param reader
     * @throws SQLException
     */
    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        statement.setClob(parameterIndex, reader);
    }

    /**
     *
     * @param parameterIndex
     * @param inputStream
     * @throws SQLException
     */
    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        statement.setBlob(parameterIndex, inputStream);
    }

    /**
     *
     * @param parameterIndex
     * @param reader
     * @throws SQLException
     */
    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        statement.setNClob(parameterIndex, reader);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @param targetSqlType
     * @param scaleOrLength
     * @throws SQLException
     */
    @Override
    public void setObject(int parameterIndex, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
        statement.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @param targetSqlType
     * @throws SQLException
     */
    @Override
    public void setObject(int parameterIndex, Object x, SQLType targetSqlType) throws SQLException {
        statement.setObject(parameterIndex, x, targetSqlType);
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public long executeLargeUpdate() throws SQLException {
        return statement.executeLargeUpdate();
    }

    /**
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        return statement.executeQuery(sql);
    }

    /**
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    @Override
    public int executeUpdate(String sql) throws SQLException {
        return statement.executeUpdate(sql);
    }

    /**
     *
     * @throws SQLException
     */
    @Override
    public void close() throws SQLException {
        statement.close();
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public int getMaxFieldSize() throws SQLException {
        return statement.getMaxFieldSize();
    }

    /**
     *
     * @param max
     * @throws SQLException
     */
    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        statement.setMaxFieldSize(max);
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public int getMaxRows() throws SQLException {
        return statement.getMaxRows();
    }

    /**
     *
     * @param max
     * @throws SQLException
     */
    @Override
    public void setMaxRows(int max) throws SQLException {
        statement.setMaxRows(max);
    }

    /**
     *
     * @param enable
     * @throws SQLException
     */
    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        statement.setEscapeProcessing(enable);
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public int getQueryTimeout() throws SQLException {
        return statement.getQueryTimeout();
    }

    /**
     *
     * @param seconds
     * @throws SQLException
     */
    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        statement.setQueryTimeout(seconds);
    }

    /**
     *
     * @throws SQLException
     */
    @Override
    public void cancel() throws SQLException {
        statement.cancel();
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public SQLWarning getWarnings() throws SQLException {
        return statement.getWarnings();
    }

    /**
     *
     * @throws SQLException
     */
    @Override
    public void clearWarnings() throws SQLException {
        statement.clearWarnings();
    }

    /**
     *
     * @param name
     * @throws SQLException
     */
    @Override
    public void setCursorName(String name) throws SQLException {
        statement.setCursorName(name);
    }

    /**
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    @Override
    public boolean execute(String sql) throws SQLException {
        return statement.execute(sql);
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public ResultSet getResultSet() throws SQLException {
        return statement.getResultSet();
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public int getUpdateCount() throws SQLException {
        return statement.getUpdateCount();
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public boolean getMoreResults() throws SQLException {
        return statement.getMoreResults();
    }

    /**
     *
     * @param direction
     * @throws SQLException
     */
    @Override
    public void setFetchDirection(int direction) throws SQLException {
        statement.setFetchDirection(direction);
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public int getFetchDirection() throws SQLException {
        return statement.getFetchDirection();
    }

    /**
     *
     * @param rows
     * @throws SQLException
     */
    @Override
    public void setFetchSize(int rows) throws SQLException {
        statement.setFetchSize(rows);
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public int getFetchSize() throws SQLException {
        return statement.getFetchSize();
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public int getResultSetConcurrency() throws SQLException {
        return statement.getResultSetConcurrency();
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public int getResultSetType() throws SQLException {
        return statement.getResultSetType();
    }

    /**
     *
     * @param sql
     * @throws SQLException
     */
    @Override
    public void addBatch(String sql) throws SQLException {
        statement.addBatch(sql);
    }

    /**
     *
     * @throws SQLException
     */
    @Override
    public void clearBatch() throws SQLException {
        statement.clearBatch();
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public int[] executeBatch() throws SQLException {
        return statement.executeBatch();
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public Connection getConnection() throws SQLException {
        return statement.getConnection();
    }

    /**
     *
     * @param current
     * @return
     * @throws SQLException
     */
    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return statement.getMoreResults(current);
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return statement.getGeneratedKeys();
    }

    /**
     *
     * @param sql
     * @param autoGeneratedKeys
     * @return
     * @throws SQLException
     */
    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return statement.executeUpdate(sql, autoGeneratedKeys);
    }

    /**
     *
     * @param sql
     * @param columnIndexes
     * @return
     * @throws SQLException
     */
    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return statement.executeUpdate(sql, columnIndexes);
    }

    /**
     *
     * @param sql
     * @param columnNames
     * @return
     * @throws SQLException
     */
    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        return statement.executeUpdate(sql, columnNames);
    }

    /**
     *
     * @param sql
     * @param autoGeneratedKeys
     * @return
     * @throws SQLException
     */
    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return statement.execute(sql, autoGeneratedKeys);
    }

    /**
     *
     * @param sql
     * @param columnIndexes
     * @return
     * @throws SQLException
     */
    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return statement.execute(sql, columnIndexes);
    }

    /**
     *
     * @param sql
     * @param columnNames
     * @return
     * @throws SQLException
     */
    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return statement.execute(sql, columnNames);
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public int getResultSetHoldability() throws SQLException {
        return statement.getResultSetHoldability();
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public boolean isClosed() throws SQLException {
        return statement.isClosed();
    }

    /**
     *
     * @param poolable
     * @throws SQLException
     */
    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        statement.setPoolable(poolable);
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public boolean isPoolable() throws SQLException {
        return statement.isPoolable();
    }

    /**
     *
     * @throws SQLException
     */
    @Override
    public void closeOnCompletion() throws SQLException {
        statement.closeOnCompletion();
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return statement.isCloseOnCompletion();
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public long getLargeUpdateCount() throws SQLException {
        return statement.getLargeUpdateCount();
    }

    /**
     *
     * @param max
     * @throws SQLException
     */
    @Override
    public void setLargeMaxRows(long max) throws SQLException {
        statement.setLargeMaxRows(max);
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public long getLargeMaxRows() throws SQLException {
        return statement.getLargeMaxRows();
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public long[] executeLargeBatch() throws SQLException {
        return statement.executeLargeBatch();
    }

    /**
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    @Override
    public long executeLargeUpdate(String sql) throws SQLException {
        return statement.executeLargeUpdate(sql);
    }

    /**
     *
     * @param sql
     * @param autoGeneratedKeys
     * @return
     * @throws SQLException
     */
    @Override
    public long executeLargeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return statement.executeLargeUpdate(sql, autoGeneratedKeys);
    }

    /**
     *
     * @param sql
     * @param columnIndexes
     * @return
     * @throws SQLException
     */
    @Override
    public long executeLargeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return statement.executeLargeUpdate(sql, columnIndexes);
    }

    /**
     *
     * @param sql
     * @param columnNames
     * @return
     * @throws SQLException
     */
    @Override
    public long executeLargeUpdate(String sql, String[] columnNames) throws SQLException {
        return statement.executeLargeUpdate(sql, columnNames);
    }

    /**
     *
     * @param parameterIndex
     * @param x
     * @param length
     * @throws SQLException
     */
    @Override
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        statement.setUnicodeStream(parameterIndex, x, length);
    }

    /**
     *
     * @param iface
     * @param <T>
     * @return
     * @throws SQLException
     */
    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return statement.unwrap(iface);
    }

    /**
     *
     * @param iface
     * @return
     * @throws SQLException
     */
    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return statement.isWrapperFor(iface);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return statement.toString();
    }
}
