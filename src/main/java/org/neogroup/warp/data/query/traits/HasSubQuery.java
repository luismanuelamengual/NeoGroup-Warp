package org.neogroup.warp.data.query.traits;

import org.neogroup.warp.data.query.SelectQuery;

public interface HasSubQuery<R extends HasSubQuery<R>> {

    SelectQuery getSubQuery();

    R setSubQuery(SelectQuery subQuery);
}
