package org.neogroup.warp.datasources.query;

import java.util.ArrayList;
import java.util.List;

public class QueryConditionGroup extends QueryCondition {

    private QueryConditionGroupConnector connector;
    private final List<QueryCondition> conditions;

    public QueryConditionGroup() {
        this(QueryConditionGroupConnector.AND);
    }

    public QueryConditionGroup(QueryConditionGroupConnector connector) {
        this.connector = connector;
        conditions = new ArrayList<>();
    }

    public QueryConditionGroupConnector getConnector() {
        return connector;
    }

    public void setConnector(QueryConditionGroupConnector connector) {
        this.connector = connector;
    }

    public void clearConditions () {
        conditions.clear();
    }

    public void addCondition (QueryCondition condition) {
        conditions.add(condition);
    }
}
