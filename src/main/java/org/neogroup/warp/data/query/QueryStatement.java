package org.neogroup.warp.data.query;

public class QueryStatement {

    public static final char SPACE = ' ';
    public static final char COMMA = ',';
    public static final char BACK_SLASH = '\\';
    public static final char QUOTATION_MARK = '\'';
    public static final char DOUBLE_QUOTATION_MARK = '"';
    public static final char OPENING_PARENTHESIS = '(';
    public static final char CLOSING_PARENTHESIS = ')';

    public static final String SELECT = "SELECT";
    public static final String INSERT = "INSERT";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";
    public static final String INTO = "INTO";
    public static final String SET = "SET";
    public static final String VALUES = "VALUES";
    public static final String DISTINCT = "DISTINCT";
    public static final String ALL = "*";
    public static final String AS = "AS";
    public static final String POINT = ".";
    public static final String FROM = "FROM";
    public static final String AND = "AND";
    public static final String OR = "OR";
    public static final String NULL = "NULL";
    public static final String IS = "IS";
    public static final String NOT = "NOT";
    public static final String IN = "IN";
    public static final String ON = "ON";
    public static final String WHERE = "WHERE";
    public static final String HAVING = "HAVING";
    public static final String GROUP = "GROUP";
    public static final String ORDER = "ORDER";
    public static final String BY = "BY";
    public static final String LIMIT = "LIMIT";
    public static final String OFFSET = "OFFSET";
    public static final String LIKE = "LIKE";
    public static final String LIKE_WILDCARD = "%";
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";
    public static final String JOIN = "JOIN";
    public static final String INNER_JOIN = "INNER JOIN";
    public static final String OUTER_JOIN = "OUTER JOIN";
    public static final String LEFT_JOIN = "LEFT JOIN";
    public static final String RIGHT_JOIN = "RIGHT JOIN";
    public static final String CROSS_JOIN = "CROSS JOIN";
    public static final String EQUALS_OPERATOR = "=";
    public static final String DISTINCT_OPERATOR = "!=";
    public static final String GREATER_THAN_OPERATOR = ">";
    public static final String GREATER_OR_EQUALS_THAN_OPERATOR = ">=";
    public static final String LOWER_THAN_OPERATOR = "<";
    public static final String LOWER_OR_EQUALS_THAN_OPERATOR = "<=";
    public static final String WILDCARD;

    static {
        WILDCARD = "?";
    }
}