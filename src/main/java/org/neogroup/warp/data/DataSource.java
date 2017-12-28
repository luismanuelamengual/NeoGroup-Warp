package org.neogroup.warp.data;

import java.sql.Connection;

public abstract class DataSource {

    protected abstract Connection requestConnection();

    public DataConnection getConnection() {
        return new DataConnection(requestConnection());
    }
}
