package org.neogroup.warp.resources;

import org.neogroup.warp.data.query.DeleteQuery;
import org.neogroup.warp.data.query.InsertQuery;
import org.neogroup.warp.data.query.SelectQuery;
import org.neogroup.warp.data.query.UpdateQuery;

import java.util.Collection;

public abstract class Resource<M extends Object>  {

    public Collection<M> find (SelectQuery query) {
        throw new UnsupportedOperationException();
    }

    public Collection<M> insert (InsertQuery query) {
        throw new UnsupportedOperationException();
    }

    public Collection<M> update (UpdateQuery query) {
        throw new UnsupportedOperationException();
    }

    public Collection<M> delete (DeleteQuery query) {
        throw new UnsupportedOperationException();
    }
}