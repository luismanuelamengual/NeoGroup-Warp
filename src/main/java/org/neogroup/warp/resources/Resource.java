package org.neogroup.warp.resources;

import org.neogroup.warp.query.Query;

import java.util.Collection;

public abstract class Resource<R>  {

    public abstract Collection<R> find(Query query);
    public abstract R insert(Query query);
    public abstract R update(Query query);
    public abstract R delete(Query query);
}