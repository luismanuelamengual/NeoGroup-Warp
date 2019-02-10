package org.neogroup.warp.resources;

import org.neogroup.warp.data.query.DeleteQuery;
import org.neogroup.warp.data.query.InsertQuery;
import org.neogroup.warp.data.query.SelectQuery;
import org.neogroup.warp.data.query.UpdateQuery;

import java.util.Collection;

public abstract class Resource<M extends Object>  {

    public M read (Object id) {
        throw new UnsupportedOperationException();
    }

    public Collection<M> read (SelectQuery query) {
        throw new UnsupportedOperationException();
    }

    public M create (M object) {
        throw new UnsupportedOperationException();
    }

    public M create (InsertQuery query) {
        throw new UnsupportedOperationException();
    }

    public M update (M object) {
        throw new UnsupportedOperationException();
    }

    public Collection<M> update (UpdateQuery query) {
        throw new UnsupportedOperationException();
    }

    public M delete (Object id) {
        throw new UnsupportedOperationException();
    }

    public Collection<M> delete (DeleteQuery query) {
        throw new UnsupportedOperationException();
    }
}