package org.neogroup.warp.resources;

import org.neogroup.warp.data.DataCollection;
import org.neogroup.warp.data.query.DeleteQuery;
import org.neogroup.warp.data.query.InsertQuery;
import org.neogroup.warp.data.query.SelectQuery;
import org.neogroup.warp.data.query.UpdateQuery;

import static org.neogroup.warp.Warp.getConnection;

public class DataResource extends Resource {

    @Override
    public DataCollection find(SelectQuery query) {
        return getConnection().query(query);
    }

    @Override
    public DataCollection insert(InsertQuery query) {
        getConnection().execute(query);
        return null;
    }

    @Override
    public DataCollection update(UpdateQuery query) {
        getConnection().execute(query);
        return null;
    }

    @Override
    public DataCollection delete(DeleteQuery query) {
        getConnection().execute(query);
        return null;
    }
}