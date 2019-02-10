package org.neogroup.warp.data;

import org.neogroup.warp.data.query.builders.DefaultQueryBuilder;
import org.neogroup.warp.data.query.builders.QueryBuilder;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DataSource {

    private QueryBuilder queryBuilder;

    public DataSource () {
        this(new DefaultQueryBuilder());
    }

    public DataSource(QueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
    }

    public DataConnection getConnection () {
        try {
            return new DataConnection(requestConnection(), queryBuilder);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    protected abstract Connection requestConnection() throws SQLException;
}