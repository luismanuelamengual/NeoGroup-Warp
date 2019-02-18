package org.neogroup.warp.data.query.traits;

public interface HasDistinct<R> {

    Boolean isDistinct();

    R setDistinct(Boolean distinct);
}
