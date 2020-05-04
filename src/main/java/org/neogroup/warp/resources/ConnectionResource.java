package org.neogroup.warp.resources;

import org.neogroup.warp.data.DataConnection;
import org.neogroup.warp.data.DataObject;
import org.neogroup.warp.data.query.DeleteQuery;
import org.neogroup.warp.data.query.InsertQuery;
import org.neogroup.warp.data.query.SelectQuery;
import org.neogroup.warp.data.query.UpdateQuery;

import java.util.Collection;

public class ConnectionResource extends Resource<DataObject> {

    private DataConnection connection;

    public ConnectionResource(DataConnection connection) {
        this.connection = connection;
    }

    @Override
    public Collection<DataObject> find(SelectQuery query) {
        return connection.query(query);
    }

    @Override
    public Collection<DataObject> insert(InsertQuery query) {
        connection.execute(query);
        return null;
    }

    @Override
    public Collection<DataObject> update(UpdateQuery query) {
        connection.execute(query);
        return null;
    }

    @Override
    public Collection<DataObject> delete(DeleteQuery query) {
        connection.execute(query);
        return null;
    }
}