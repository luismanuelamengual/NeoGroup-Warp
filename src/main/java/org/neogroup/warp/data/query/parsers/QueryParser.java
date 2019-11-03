package org.neogroup.warp.data.query.parsers;

import org.neogroup.warp.data.query.Query;

public abstract class QueryParser {

    public abstract Query parseQuery (String query);
}