package org.neogroup.warp.data.query.traits;

import org.neogroup.warp.data.query.SelectQuery;

public interface HasTableSubQuery<R> {

    SelectQuery getTableSubQuery();

    R setTableSubQuery(SelectQuery subQuery);
}
