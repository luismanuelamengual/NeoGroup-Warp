package org.neogroup.warp.data.query.traits;

public interface HasTableAlias<R extends HasTableAlias<R>> {

    String getTableAlias();

    R setTableAlias(String tableAlias);
}
