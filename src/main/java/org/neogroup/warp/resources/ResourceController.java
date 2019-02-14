package org.neogroup.warp.resources;

import org.neogroup.warp.Request;
import org.neogroup.warp.data.query.SelectQuery;
import org.neogroup.warp.data.query.fields.SortDirection;

import java.util.Collection;
import java.util.Set;

public class ResourceController<M extends Object> {

    private static final String FIELDS_PARAMETER = "fields";
    private static final String SORT_PARAMETER = "sort";
    private static final String OFFSET_PARAMETER = "offset";
    private static final String LIMIT_PARAMETER = "limit";
    private static final String SEPARATOR = ",";
    private static final String MINUS = "-";

    private String resourceName;
    private Resource<M> resource;

    public ResourceController(String resourceName, Resource<M> resource) {
        this.resourceName = resourceName;
        this.resource = resource;
    }

    public M getResource (Request request) {
        Object id = request.get("id");
        return resource.find(id);
    }

    public Collection<M> getResources (Request request) {
        SelectQuery query = new SelectQuery();
        query.setTableName(resourceName);
        Set<String> parameters = request.getParameterNames();
        for (String parameter : parameters) {
            Object parameterValue = request.get(parameter);
            switch (parameter) {
                case FIELDS_PARAMETER:
                    String[] fields = parameterValue.toString().split(SEPARATOR);
                    query.select(fields);
                    break;
                case SORT_PARAMETER:
                    String[] sortFields = parameterValue.toString().split(SEPARATOR);
                    for(String sortField : sortFields) {
                        SortDirection direction = SortDirection.ASC;
                        if (sortField.startsWith(MINUS)) {
                            direction = SortDirection.DESC;
                            sortField = sortField.substring(1);
                        }
                        query.orderBy(sortField, direction);
                    }
                    break;
                case OFFSET_PARAMETER:
                    query.offset(Integer.parseInt(parameterValue.toString()));
                    break;
                case LIMIT_PARAMETER:
                    query.limit(Integer.parseInt(parameterValue.toString()));
                    break;
                default:
                    query.where(parameter, parameterValue);
                    break;
            }
        }
        return resource.find(query);
    }
}
