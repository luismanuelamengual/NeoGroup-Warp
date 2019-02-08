package org.neogroup.warp.data.query.builders;

import org.neogroup.warp.data.query.Query;

import java.util.List;

public abstract class QueryBuilder {

    public abstract String buildQuery (Query query);

    public abstract String buildQuery (Query query, List<Object> bindings);
}
