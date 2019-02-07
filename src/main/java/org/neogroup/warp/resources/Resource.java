package org.neogroup.warp.resources;

import org.neogroup.warp.data.query.Query;

import java.util.Collection;

public abstract class Resource<R>  {

    public Collection<R> find(Query query) {
        throw new RuntimeException ("Unimplemented \"find\" method !!");
    }

    public R insert(Query query) {
        throw new RuntimeException ("Unimplemented \"insert\" method !!");
    }

    public R update(Query query) {
        throw new RuntimeException ("Unimplemented \"update\" method !!");
    }

    public R delete(Query query) {
        throw new RuntimeException ("Unimplemented \"delete\" method !!");
    }
}