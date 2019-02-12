package org.neogroup.warp.resources;

import org.neogroup.warp.data.DataCollection;
import org.neogroup.warp.data.query.DeleteQuery;
import org.neogroup.warp.data.query.InsertQuery;
import org.neogroup.warp.data.query.SelectQuery;
import org.neogroup.warp.data.query.UpdateQuery;

public abstract class Resource  {

    public DataCollection find (SelectQuery query) {
        throw new UnsupportedOperationException();
    }

    public DataCollection insert (InsertQuery query) {
        throw new UnsupportedOperationException();
    }

    public DataCollection update (UpdateQuery query) {
        throw new UnsupportedOperationException();
    }

    public DataCollection delete (DeleteQuery query) {
        throw new UnsupportedOperationException();
    }
}