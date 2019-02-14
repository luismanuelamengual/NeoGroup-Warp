package org.neogroup.warp.resources;

import org.neogroup.warp.data.query.DeleteQuery;
import org.neogroup.warp.data.query.InsertQuery;
import org.neogroup.warp.data.query.SelectQuery;
import org.neogroup.warp.data.query.UpdateQuery;

import java.util.Collection;

public abstract class Resource<M extends Object>  {

    public M find (Object id) {
        throw new UnsupportedOperationException();
    }

    public Collection<M> find () {
        return find(null);
    }

    public Collection<M> find (SelectQuery query) {
        throw new UnsupportedOperationException();
    }

    public M insert (M object) {
        throw new UnsupportedOperationException();
    }

    public M insert (InsertQuery query) {
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