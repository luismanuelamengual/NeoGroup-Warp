package org.neogroup.warp.resources;

import org.neogroup.warp.data.query.*;

import java.util.Collection;

public abstract class Resource<R>  {

    public Collection<R> find(SelectQuery query) {
        throw new RuntimeException ("Unimplemented \"find\" method !!");
    }

    public R insert(InsertQuery query) {
        throw new RuntimeException ("Unimplemented \"insert\" method !!");
    }

    public R update(UpdateQuery query) {
        throw new RuntimeException ("Unimplemented \"update\" method !!");
    }

    public R delete(DeleteQuery query) {
        throw new RuntimeException ("Unimplemented \"delete\" method !!");
    }
}