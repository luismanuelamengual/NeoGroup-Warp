package org.neogroup.warp.data.query;

public class SQLParser {
/*
    public static final String WHILDCARD = "*";
    public static final String SEPARATOR = " ";
    public static final String SCOPE_SEPARATOR = ".";
    public static final String FIELDS_SEPARATOR = ",";

    public static final String AS = "AS";
    public static final String SELECT = "SELECT";
    public static final String FROM = "FROM";
    public static final String UNION = "UNION";

    public String getSQL (Query query, Map<String,Object> parameters) {

        String sql = null;
        if (query instanceof RequestQuery) {
            sql = getRequestSQL((RequestQuery)query, parameters);
        }
        return sql;
    }

    private String getRequestSQL (RequestQuery requestQuery, Map<String,Object> parameters) {

        String sql = null;
        if (requestQuery instanceof UnionQuery) {
            sql = getUnionSQL((UnionQuery)requestQuery, parameters);
        }
        else if (requestQuery instanceof Select) {
            sql = getSelectSQL((Select)requestQuery, parameters);
        }
        return sql;
    }

    private String getUnionSQL(UnionQuery unionQuery, Map<String, Object> parameters) {

        StringBuilder sql = new StringBuilder();
        Iterator<Select> iterator = unionQuery.getSelectQueries().iterator();
        while (iterator.hasNext()) {
            Select query = iterator.next();
            sql.append(getSelectSQL(query, parameters));
            if (iterator.hasNext()) {
                sql.append(UNION);
            }
        }
        return sql.toString();
    }

    private String getSelectSQL(Select select, Map<String, Object> parameters) {

        StringBuilder sql = new StringBuilder();
        sql.append(SELECT);

        if (!select.getSelectFields().isEmpty()) {
            sql.append(SEPARATOR);
            sql.append(WHILDCARD);
        }
        else {
            Iterator<ColumnReturnField> returnFieldsIterator = select.getSelectFields().iterator();
            while (returnFieldsIterator.hasNext()) {
                ColumnReturnField returnField = returnFieldsIterator.next();
                sql.append(SEPARATOR);
                if (returnField.getTableName() != null) {
                    sql.append(returnField.getTableName());
                    sql.append(SCOPE_SEPARATOR);
                }
                sql.append(returnField.getColumnName());
                if (returnField.getFieldAlias() != null) {
                    sql.append(SEPARATOR);
                    sql.append(AS);
                    sql.append(SEPARATOR);
                    sql.append(returnField.getFieldAlias());
                }
                if (returnFieldsIterator.hasNext()) {
                    sql.append(FIELDS_SEPARATOR);
                }
            }
        }

        sql.append(SEPARATOR);
        sql.append(FROM);
        sql.append(SEPARATOR);
        sql.append(select.getTableName());



        return sql.toString();
    }
*/
    /*
    public String toString() {

        Map<String,Object> parameters = new HashMap<>();
        String sql = toString(parameters);
        for (String parameter : parameters.keySet()) {
            Object parameterValue = parameters.get(parameter);
            String parameterValueString = null;
            if ((parameterValue instanceof Number) || (parameterValue instanceof Boolean)) {
                parameterValueString = parameterValue.toString();
            }
            else {
                parameterValueString = "\"" + parameterValue.toString() + "\"";
            }
            sql = sql.replaceAll(":" + parameter, parameterValueString);
        }
        return sql;
    }
    */

/*
    private void buildFilerSQL(QueryCondition condition, StringBuilder sql, Map<String, Object> parameters) {

        if (filter instanceof EntityFilterGroup) {
            EntityFilterGroup resourceFilterGroup = (EntityFilterGroup)filter;
            sql.append("(");
            Iterator<EntityFilter> resourceFilterIterator = resourceFilterGroup.getFilters().iterator();
            while (resourceFilterIterator.hasNext()) {
                EntityFilter childEntityFilter = resourceFilterIterator.next();
                buildFilterSQL(childEntityFilter, sql, sqlParameters);

                if (resourceFilterIterator.hasNext()) {
                    switch (resourceFilterGroup.getConnector()) {
                        case AND:
                            sql.append(" AND ");
                            break;
                        case OR:
                            sql.append(" OR ");
                            break;
                    }
                }
            }
            sql.append(")");
        }@Override
    public String toString(Map<String, Object> parameters) {

        StringBuilder sql = new StringBuilder();
        Iterator<SelectQuery> iterator = selectQueries.iterator();
        while (iterator.hasNext()) {
            SelectQuery query = iterator.next();
            sql.append(query.toString(parameters));
            if (iterator.hasNext()) {
                sql.append(SQL.UNION);
            }
        }
        return sql.toString();
    }
        else if (filter instanceof EntityPropertyFilter) {
            EntityPropertyFilter resourcePropertyFilter = (EntityPropertyFilter)filter;
            for (Field field : entityClass.getDeclaredFields()) {
                if (field.getName().equals(resourcePropertyFilter.getProperty())) {
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    if (columnAnnotation != null) {
                        switch (resourcePropertyFilter.getOperator()) {
                            case EntityPropertyOperator.EQUALS:
                                sql.append(columnAnnotation.name());
                                sql.append("=");
                                sql.append("?");
                                sqlParameters.add(resourcePropertyFilter.getValue());
                                break;
                            case EntityPropertyOperator.DISTINCT:
                                sql.append(columnAnnotation.name());
                                sql.append("!=");
                                sql.append("?");
                                sqlParameters.add(resourcePropertyFilter.getValue());
                                break;
                            case EntityPropertyOperator.GREATER_THAN:
                                sql.append(columnAnnotation.name());
                                sql.append(">");
                                sql.append("?");
                                sqlParameters.add(resourcePropertyFilter.getValue());
                                break;
                            case EntityPropertyOperator.GREATER_OR_EQUALS_THAN:
                                sql.append(columnAnnotation.name());
                                sql.append(">=");
                                sql.append("?");
                                sqlParameters.add(resourcePropertyFilter.getValue());
                                break;
                            case EntityPropertyOperator.LESS_THAN:
                                sql.append(columnAnnotation.name());
                                sql.append("<");
                                sql.append("?");
                                sqlParameters.add(resourcePropertyFilter.getValue());
                                break;
                            case EntityPropertyOperator.LESS_OR_EQUALS_THAN:
                                sql.append(columnAnnotation.name());
                                sql.append("<=");
                                sql.append("?");
                                sqlParameters.add(resourcePropertyFilter.getValue());
                                break;
                            case EntityPropertyOperator.CONTAINS:
                                sql.append(columnAnnotation.name());
                                sql.append(" LIKE ");
                                sql.append("?");
                                sqlParameters.add("%" + resourcePropertyFilter.getValue() + "%");
                                break;
                            case EntityPropertyOperator.NOT_CONTAINS:
                                sql.append(columnAnnotation.name());
                                sql.append(" NOT LIKE ");
                                sql.append("?");
                                sqlParameters.add("%" + resourcePropertyFilter.getValue() + "%");
                                break;
                            case EntityPropertyOperator.IN: {
                                sql.append(columnAnnotation.name());
                                sql.append(" IN ");
                                sql.append("(");
                                Collection valueCollection = (Collection) resourcePropertyFilter.getValue();
                                Iterator valueCollectionIterator = valueCollection.iterator();
                                while (valueCollectionIterator.hasNext()) {
                                    Object singleValue = valueCollectionIterator.next();
                                    sql.append("?");
                                    sqlParameters.add(singleValue);
                                    if (valueCollectionIterator.hasNext()) {
                                        sql.append(",");
                                    }
                                }
                                sql.append(")");
                                break;
                            }
                            case EntityPropertyOperator.NOT_IN: {
                                sql.append(columnAnnotation.name());
                                sql.append(" NOT IN ");
                                sql.append("(");
                                Collection valueCollection = (Collection) resourcePropertyFilter.getValue();
                                Iterator valueCollectionIterator = valueCollection.iterator();
                                while (valueCollectionIterator.hasNext()) {
                                    Object singleValue = valueCollectionIterator.next();
                                    sql.append("?");
                                    sqlParameters.add(singleValue);
                                    if (valueCollectionIterator.hasNext()) {
                                        sql.append(",");
                                    }
                                }
                                sql.append(")");
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
    }
    */
}
