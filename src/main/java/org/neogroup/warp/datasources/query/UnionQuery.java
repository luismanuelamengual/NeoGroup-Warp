package org.neogroup.warp.datasources.query;

import java.util.List;

public class UnionQuery extends RequestQuery {

    private List<SelectQuery> selectQueries;

    public List<SelectQuery> getSelectQueries() {
        return selectQueries;
    }

    public UnionQuery clearSelectQueries() {
        selectQueries.clear();
        return this;
    }

    public UnionQuery addSelectQuery(SelectQuery query) {
        selectQueries.add(query);
        return this;
    }
}
