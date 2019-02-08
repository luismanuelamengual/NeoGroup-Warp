package org.neogroup.warp.data.query.builders;

import org.neogroup.warp.data.query.*;
import org.neogroup.warp.data.query.conditions.*;
import org.neogroup.warp.data.query.fields.Field;
import org.neogroup.warp.data.query.fields.SelectField;
import org.neogroup.warp.data.query.fields.SortDirection;
import org.neogroup.warp.data.query.fields.SortField;
import org.neogroup.warp.data.query.joins.Join;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DefaultQueryBuilder extends QueryBuilder {

    private static final String SPACE = " ";
    private static final String COMMA = ",";
    private static final String DOUBLE_QUOTES = "\"";
    private static final String PARENTHESIS_START = "(";
    private static final String PARENTHESIS_END = ")";
    private static final String SELECT = "SELECT";
    private static final String INSERT = "INSERT";
    private static final String UPDATE = "UPDATE";
    private static final String DELETE = "DELETE";
    private static final String INTO = "INTO";
    private static final String SET = "SET";
    private static final String VALUES = "VALUES";
    private static final String DISTINCT = "DISTINCT";
    private static final String ALL = "*";
    private static final String AS = "AS";
    private static final String POINT = ".";
    private static final String FROM = "FROM";
    private static final String AND = "AND";
    private static final String OR = "OR";
    private static final String NULL = "NULL";
    private static final String IS = "IS";
    private static final String NOT = "NOT";
    private static final String IN = "IN";
    private static final String ON = "ON";
    private static final String WHERE = "WHERE";
    private static final String HAVING = "HAVING";
    private static final String GROUP = "GROUP";
    private static final String ORDER = "ORDER";
    private static final String BY = "BY";
    private static final String LIMIT = "LIMIT";
    private static final String OFFSET = "OFFSET";
    private static final String LIKE = "LIKE";
    private static final String LIKE_WILDCARD = "%";
    private static final String ASC = "ASC";
    private static final String DESC = "DESC";
    private static final String JOIN = "JOIN";
    private static final String INNER = "INNER";
    private static final String OUTER = "OUTER";
    private static final String LEFT = "LEFT";
    private static final String RIGHT = "RIGHT";
    private static final String CROSS = "CROSS";
    private static final String OPERATOR_EQUALS = "=";
    private static final String OPERATOR_DISTINCT = "!=";
    private static final String OPERATOR_GREATER_THAN = ">";
    private static final String OPERATOR_GREATER_OR_EQUALS_THAN = ">=";
    private static final String OPERATOR_LOWER_THAN = "<";
    private static final String OPERATOR_LOWER_OR_EQUALS_THAN = "<=";
    private static final String WILDCARD;

    static {
        WILDCARD = "?";
    }

    @Override
    public String buildQuery(Query query) {
        List<Object> bindings = new ArrayList<>();
        String sql = buildQuery(query, bindings);
        for (Object binding : bindings) {
            String replacement = binding.toString();
            if (binding instanceof String) {
                replacement = DOUBLE_QUOTES + replacement + DOUBLE_QUOTES;
            }
            sql = sql.replaceFirst("\\?", replacement);
        }
        return sql;
    }

    @Override
    public String buildQuery(Query query, List<Object> bindings) {
        StringWriter writer = new StringWriter();
        buildQuery(writer, query, bindings);
        return writer.toString();
    }

    protected void buildQuery (StringWriter writer, Query query, List<Object> bindings) {
        if (query instanceof SelectQuery) {
            buildSelectQuery(writer, (SelectQuery)query, bindings);
        }
        else if (query instanceof InsertQuery) {
            buildInsertQuery(writer, (InsertQuery)query, bindings);
        }
        else if (query instanceof UpdateQuery) {
            buildUpdateQuery(writer, (UpdateQuery)query, bindings);
        }
        else if (query instanceof DeleteQuery) {
            buildDeleteQuery(writer, (DeleteQuery)query, bindings);
        }
    }

    protected void buildSelectQuery (StringWriter writer, SelectQuery query, List<Object> bindings) {
        writer.write(SELECT);
        if (query.isDistinct()) {
            writer.write(SPACE);
            writer.write(DISTINCT);
        }
        writer.write(SPACE);
        List<SelectField> selectFields = query.getSelectFields();
        if (selectFields.isEmpty()) {
            writer.write(ALL);
        }
        else {
            boolean isFirst = true;
            for (SelectField selectField : selectFields) {
                if (!isFirst) {
                    writer.write(COMMA);
                    writer.write(SPACE);
                }
                buildSelectField(writer, selectField, bindings);
                isFirst = false;
            }
        }
        writer.write(SPACE);
        writer.write(FROM);
        writer.write(SPACE);
        SelectQuery subQuery = query.getSubQuery();
        if (subQuery != null) {
            writer.write(PARENTHESIS_START);
            buildSelectQuery(writer, subQuery, bindings);
            writer.write(PARENTHESIS_END);
        }
        else {
            writer.write(query.getTableName());
            String tableAlias = query.getTableAlias();
            if (tableAlias != null) {
                writer.write(SPACE);
                writer.write(AS);
                writer.write(SPACE);
                writer.write(tableAlias);
            }
        }

        List<Join> joins = query.getJoins();
        if (joins != null) {
            for (Join join : joins) {
                writer.write(SPACE);
                buildJoin(writer, join, bindings);
            }
        }

        ConditionGroup whereConditionGroup = query.getWhereConditions();
        if (!whereConditionGroup.isEmpty()) {
            writer.write(SPACE);
            writer.write(WHERE);
            writer.write(SPACE);
            buildConditionGroup(writer, whereConditionGroup, bindings);
        }

        List<Field> groupByFields = query.getGroupByFields();
        if (!groupByFields.isEmpty()) {
            writer.write(SPACE);
            writer.write(GROUP);
            writer.write(SPACE);
            writer.write(BY);
            writer.write(SPACE);
            boolean isFirst = true;
            for (Field groupByField : groupByFields) {
                if (!isFirst) {
                    writer.write(COMMA);
                    writer.write(SPACE);
                }
                buildField(writer, groupByField, bindings);
                isFirst = false;
            }
        }

        List<SortField> orderByFields = query.getOrderByFields();
        if (!orderByFields.isEmpty()) {
            writer.write(SPACE);
            writer.write(ORDER);
            writer.write(SPACE);
            writer.write(BY);
            writer.write(SPACE);
            boolean isFirst = true;
            for (SortField orderByField : orderByFields) {
                if (!isFirst) {
                    writer.write(COMMA);
                    writer.write(SPACE);
                }
                buildOrderByField(writer, orderByField, bindings);
                isFirst = false;
            }
        }

        ConditionGroup havingConditionGroup = query.getHavingConditions();
        if (!havingConditionGroup.isEmpty()) {
            writer.write(SPACE);
            writer.write(HAVING);
            writer.write(SPACE);
            buildConditionGroup(writer, havingConditionGroup, bindings);
        }

        Integer offset = query.getOffset();
        if (offset != null) {
            writer.write(SPACE);
            writer.write(OFFSET);
            writer.write(SPACE);
            writer.write(offset.toString());
        }

        Integer limit = query.getLimit();
        if (limit != null) {
            writer.write(SPACE);
            writer.write(LIMIT);
            writer.write(SPACE);
            writer.write(limit.toString());
        }
    }

    protected void buildInsertQuery (StringWriter writer, InsertQuery query, List<Object> bindings) {
        writer.write(INSERT);
        writer.write(SPACE);
        writer.write(INTO);
        writer.write(SPACE);
        writer.write(query.getTableName());
        writer.write(SPACE);
        writer.write(PARENTHESIS_START);
        Map<String, Object> fields = query.getFields();
        boolean isFirst = true;
        for (String fieldName : fields.keySet()) {
            if (!isFirst) {
                writer.write(COMMA);
                writer.write(SPACE);
            }
            writer.write(fieldName);
            isFirst = false;
        }
        writer.write(PARENTHESIS_END);
        writer.write(SPACE);
        writer.write(VALUES);
        writer.write(SPACE);
        writer.write(PARENTHESIS_START);
        isFirst = true;
        for (Object fieldValue : fields.values()) {
            if (!isFirst) {
                writer.write(COMMA);
                writer.write(SPACE);
            }
            writer.write(WILDCARD);
            bindings.add(fieldValue);
            isFirst = false;
        }
        writer.write(PARENTHESIS_END);
    }

    protected void buildUpdateQuery (StringWriter writer, UpdateQuery query, List<Object> bindings) {
        writer.write(UPDATE);
        writer.write(SPACE);
        writer.write(query.getTableName());
        writer.write(SPACE);
        writer.write(SET);
        writer.write(SPACE);
        boolean isFirst = true;
        Map<String, Object> fields = query.getFields();
        for (String fieldName : fields.keySet()) {
            if (!isFirst) {
                writer.write(COMMA);
                writer.write(SPACE);
            }
            Object fieldValue = fields.get(fieldName);
            writer.write(fieldName);
            writer.write(SPACE);
            writer.write(OPERATOR_EQUALS);
            writer.write(SPACE);
            writer.write(WILDCARD);
            bindings.add(fieldValue);
            isFirst = false;
        }

        ConditionGroup whereConditionGroup = query.getWhereConditions();
        if (!whereConditionGroup.isEmpty()) {
            writer.write(SPACE);
            writer.write(WHERE);
            writer.write(SPACE);
            buildConditionGroup(writer, whereConditionGroup, bindings);
        }
    }

    protected void buildDeleteQuery (StringWriter writer, DeleteQuery query, List<Object> bindings) {
        writer.write(DELETE);
        writer.write(SPACE);
        writer.write(FROM);
        writer.write(query.getTableName());
        writer.write(SPACE);
        ConditionGroup whereConditionGroup = query.getWhereConditions();
        if (!whereConditionGroup.isEmpty()) {
            writer.write(SPACE);
            writer.write(WHERE);
            writer.write(SPACE);
            buildConditionGroup(writer, whereConditionGroup, bindings);
        }
    }

    protected void buildSelectField (StringWriter writer, SelectField field, List<Object> bindings) {
        buildField (writer, field, bindings);
        String alias = field.getAlias();
        if (alias != null) {
            writer.write(SPACE);
            writer.write(AS);
            writer.write(SPACE);
            writer.write(alias);
        }
    }

    protected void buildOrderByField (StringWriter writer, SortField field, List<Object> bindings) {
        buildField (writer, field, bindings);
        SortDirection direction = field.getDirection();
        if (direction != null) {
            writer.write(SPACE);
            writer.write(direction == SortDirection.ASC? ASC : DESC);
        }
    }

    protected void buildField (StringWriter writer, Field field, List<Object> bindings) {
        String tableName = field.getTableName();
        if (tableName != null) {
            writer.write(tableName);
            writer.write(POINT);
        }
        writer.write(field.getName());
    }

    protected void buildJoin (StringWriter writer, Join join, List<Object> bindings) {
        switch (join.getType()) {
            case INNER_JOIN:
                writer.write(INNER);
                writer.write(SPACE);
                break;
            case OUTER_JOIN:
                writer.write(OUTER);
                writer.write(SPACE);
                break;
            case LEFT_JOIN:
                writer.write(LEFT);
                writer.write(SPACE);
                break;
            case RIGHT_JOIN:
                writer.write(RIGHT);
                writer.write(SPACE);
                break;
            case CROSS_JOIN:
                writer.write(CROSS);
                writer.write(SPACE);
                break;
        }
        writer.write(JOIN);
        writer.write(SPACE);
        writer.write(join.getTableName());
        String tableAlias = join.getTableAlias();
        if (tableAlias != null) {
            writer.write(SPACE);
            writer.write(AS);
            writer.write(SPACE);
            writer.write(tableAlias);
        }
        writer.write(SPACE);
        writer.write(ON);
        writer.write(SPACE);
        buildConditionGroup(writer, join, bindings);
    }

    protected void buildCondition (StringWriter writer, Condition condition, List<Object> bindings) {
        if (condition instanceof BasicCondition) {
            buildBasicCondition(writer, (BasicCondition)condition, bindings);
        }
        else if (condition instanceof ConditionGroup) {
            buildConditionGroup(writer, (ConditionGroup)condition, bindings);
        }
        else if (condition instanceof RawCondition) {
            buildRawCondition(writer, (RawCondition)condition, bindings);
        }
    }

    protected void buildBasicCondition (StringWriter writer, BasicCondition condition, List<Object> bindings) {
        ConditionOperator operator = condition.getOperator();
        if (operator == null) {
            operator = ConditionOperator.EQUALS;
        }
        buildField(writer, condition.getField(), bindings);
        switch (operator) {
            case EQUALS:
                writer.write(SPACE);
                writer.write(OPERATOR_EQUALS);
                writer.write(SPACE);
                buildValue(writer, condition.getValue(), bindings);
                break;
            case DISTINCT:
                writer.write(SPACE);
                writer.write(OPERATOR_DISTINCT);
                writer.write(SPACE);
                buildValue(writer, condition.getValue(), bindings);
                break;
            case GREATER_THAN:
                writer.write(SPACE);
                writer.write(OPERATOR_GREATER_THAN);
                writer.write(SPACE);
                buildValue(writer, condition.getValue(), bindings);
                break;
            case GREATER_OR_EQUALS_THAN:
                writer.write(SPACE);
                writer.write(OPERATOR_GREATER_OR_EQUALS_THAN);
                writer.write(SPACE);
                buildValue(writer, condition.getValue(), bindings);
                break;
            case LESS_THAN:
                writer.write(SPACE);
                writer.write(OPERATOR_LOWER_THAN);
                writer.write(SPACE);
                buildValue(writer, condition.getValue(), bindings);
                break;
            case LESS_OR_EQUALS_THAN:
                writer.write(SPACE);
                writer.write(OPERATOR_LOWER_OR_EQUALS_THAN);
                writer.write(SPACE);
                buildValue(writer, condition.getValue(), bindings);
                break;
            case NULL:
                writer.write(SPACE);
                writer.write(IS);
                writer.write(SPACE);
                writer.write(NULL);
                break;
            case NOT_NULL:
                writer.write(SPACE);
                writer.write(IS);
                writer.write(SPACE);
                writer.write(NOT);
                writer.write(SPACE);
                writer.write(NULL);
                break;
            case IN:
                writer.write(SPACE);
                writer.write(IN);
                writer.write(SPACE);
                buildValue(writer, condition.getValue(), bindings);
                break;
            case NOT_IN:
                writer.write(SPACE);
                writer.write(NOT);
                writer.write(SPACE);
                writer.write(IN);
                writer.write(SPACE);
                buildValue(writer, condition.getValue(), bindings);
                break;
            case CONTAINS:
                writer.write(SPACE);
                writer.write(LIKE);
                writer.write(SPACE);
                buildValue(writer, LIKE_WILDCARD + condition.getValue().toString() + LIKE_WILDCARD, bindings);
                break;
            case NOT_CONTAINS:
                writer.write(SPACE);
                writer.write(NOT);
                writer.write(SPACE);
                writer.write(LIKE);
                writer.write(SPACE);
                buildValue(writer, LIKE_WILDCARD + condition.getValue().toString() + LIKE_WILDCARD, bindings);
                break;
        }
    }

    protected void buildConditionGroup (StringWriter writer, ConditionGroup conditionGroup, List<Object> bindings) {
        String connector = conditionGroup.getConnector().equals(ConditionGroupConnector.AND)? AND : OR;
        boolean isFirst = true;
        for (Condition condition : conditionGroup.getConditions()) {
            if (!isFirst) {
                writer.write(SPACE);
                writer.write(connector);
                writer.write(SPACE);
            }
            if (condition instanceof ConditionGroup) {
                writer.write(PARENTHESIS_START);
                buildCondition (writer, condition, bindings);
                writer.write(PARENTHESIS_END);
            }
            else {
                buildCondition (writer, condition, bindings);
            }
            isFirst = false;
        }
    }

    protected void buildRawCondition (StringWriter writer, RawCondition condition, List<Object> bindings) {
        writer.write(condition.getStatement());
        bindings.addAll(condition.getBindings());
    }

    protected void buildValue (StringWriter writer, Object value, List<Object> bindings) {
        if (value == null) {
            writer.write(NULL);
        }
        else if (value instanceof Field) {
            buildField(writer, (Field)value, bindings);
        }
        else if (value instanceof RawStatement) {
            RawStatement rawValue = (RawStatement)value;
            writer.write(rawValue.getStatement());
            bindings.addAll(rawValue.getBindings());
        }
        else if (value instanceof Query) {
            writer.write(PARENTHESIS_START);
            buildQuery(writer, (Query)value, bindings);
            writer.write(PARENTHESIS_END);
        }
        else if (value instanceof Iterable) {
            writer.write(PARENTHESIS_START);
            boolean isFirst = true;
            for (Object valueItem : (Iterable)value) {
                if (!isFirst) {
                    writer.write(COMMA);
                    writer.write(SPACE);
                }
                buildValue(writer, valueItem, bindings);
                isFirst = false;
            }
            writer.write(PARENTHESIS_END);
        }
        else if (value instanceof Object[]) {
            writer.write(PARENTHESIS_START);
            boolean isFirst = true;
            for (Object valueItem : (Object[])value) {
                if (!isFirst) {
                    writer.write(COMMA);
                    writer.write(SPACE);
                }
                buildValue(writer, valueItem, bindings);
                isFirst = false;
            }
            writer.write(PARENTHESIS_END);
        }
        else {
            writer.write(WILDCARD);
            bindings.add(value);
        }
    }
}
