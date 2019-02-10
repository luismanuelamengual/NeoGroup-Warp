package org.neogroup.warp.resources;

import org.neogroup.warp.data.DataObject;
import org.neogroup.warp.data.query.DeleteQuery;
import org.neogroup.warp.data.query.InsertQuery;
import org.neogroup.warp.data.query.SelectQuery;
import org.neogroup.warp.data.query.UpdateQuery;

import java.util.Collection;

import static org.neogroup.warp.Warp.getConnection;

public class DefaultResource extends Resource<DataObject> {

    @Override
    public Collection<DataObject> read(SelectQuery query) {
        return getConnection().query(query);
    }

    @Override
    public DataObject create(InsertQuery query) {
        getConnection().execute(query);
        return null;
    }

    @Override
    public Collection<DataObject> update(UpdateQuery query) {
        getConnection().execute(query);
        return null;
    }

    @Override
    public Collection<DataObject> delete(DeleteQuery query) {
        getConnection().execute(query);
        return null;
    }
}