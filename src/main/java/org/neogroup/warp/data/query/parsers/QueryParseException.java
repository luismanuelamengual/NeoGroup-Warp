package org.neogroup.warp.data.query.parsers;

public class QueryParseException extends Exception {

    public QueryParseException(String s) {
        super(s);
    }

    public QueryParseException(String s, Throwable throwable) {
        super(s, throwable);
    }
}