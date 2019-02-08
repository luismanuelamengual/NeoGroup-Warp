
package org.neogroup.warp.data.query;

import org.neogroup.warp.data.query.builders.DefaultQueryBuilder;
import org.neogroup.warp.data.query.builders.QueryBuilder;

public abstract class Query {

    private final static QueryBuilder defaultQueryBuilder = new DefaultQueryBuilder();

    @Override
    public String toString() {
        return defaultQueryBuilder.buildQuery(this);
    }
}