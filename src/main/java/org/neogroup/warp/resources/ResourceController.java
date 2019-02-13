package org.neogroup.warp.resources;

import org.neogroup.warp.Request;
import org.neogroup.warp.data.query.SelectQuery;

import java.util.Collection;
import java.util.Set;

public class ResourceController<M extends Object> {

    private String resourceName;
    private Resource<M> resource;

    public ResourceController(String resourceName, Resource<M> resource) {
        this.resourceName = resourceName;
        this.resource = resource;
    }

    public Collection<M> getResources (Request request) {
        SelectQuery query = new SelectQuery();
        query.setTableName(resourceName);
        Set<String> parameters = request.getParameterNames();
        for (String parameter : parameters) {

        }
        return resource.find(query);
    }
}
