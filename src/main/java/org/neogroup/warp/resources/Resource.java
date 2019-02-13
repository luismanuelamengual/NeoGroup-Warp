package org.neogroup.warp.resources;

import org.neogroup.warp.data.query.DeleteQuery;
import org.neogroup.warp.data.query.InsertQuery;
import org.neogroup.warp.data.query.SelectQuery;
import org.neogroup.warp.data.query.UpdateQuery;

import java.util.Collection;
import java.util.Set;

public abstract class Resource<M extends Object>  {

    private static final String DEFAULT_ID_FIELD = "id";

    public String getIdField () {
        return DEFAULT_ID_FIELD;
    }

    public Set<String> getFields () {
        throw new UnsupportedOperationException();
    }

    public Collection<M> find (SelectQuery query) {
        throw new UnsupportedOperationException();
    }

    public M insert (InsertQuery query) {
        throw new UnsupportedOperationException();
    }

    public Collection<M> update (UpdateQuery query) {
        throw new UnsupportedOperationException();
    }

    public Collection<M> delete (DeleteQuery query) {
        throw new UnsupportedOperationException();
    }
}