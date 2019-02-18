package org.neogroup.warp.data.query.traits;

public interface HasOffset<R> {

    Integer getOffset();

    R offset(Integer offset);
}
