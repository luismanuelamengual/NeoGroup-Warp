package org.neogroup.warp.data.query.traits;

public interface HasLimit<R extends HasLimit<R>> {

    Integer getLimit();

    R limit(Integer limit);
}
