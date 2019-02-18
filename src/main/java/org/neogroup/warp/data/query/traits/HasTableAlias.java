package org.neogroup.warp.data.query.traits;

public interface HasTableAlias<R> {

    String getTableAlias();

    R setTableAlias(String tableAlias);
}
