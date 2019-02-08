package org.neogroup.warp.data.query.traits;

public interface HasTable<R extends HasTable<R>> {

    String getTableName();

    R setTableName(String tableName);
}
