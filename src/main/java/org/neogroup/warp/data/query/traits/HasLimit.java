package org.neogroup.warp.data.query.traits;

public interface HasLimit<R> {

    Integer getLimit();

    R limit(Integer limit);
}
