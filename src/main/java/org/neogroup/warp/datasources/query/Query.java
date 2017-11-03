package org.neogroup.warp.datasources.query;

public abstract class Query {

    public static final String AS = "AS";

    private final String tableName;

    public Query(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }
}
