package org.neogroup.warp.resources;

import org.neogroup.warp.data.query.QueryObject;

import java.util.Collection;

public class ResourceProxy<T extends Object> extends QueryObject<ResourceProxy<T>> {

    private Resource<T> resource;

    public ResourceProxy(String resourceName, Resource resource) {
        super(resourceName);
        this.resource = resource;
    }

    public Collection<T> find() {
        return resource.find(createSelectQuery());
    }

    public T insert () {
        return resource.insert(createInsertQuery());
    }

    public Collection<T> update () {
        return resource.update(createUpdateQuery());
    }

    public Collection<T> delete () {
        return resource.delete(createDeleteQuery());
    }

    public T first () {
        return limit(1).find().iterator().next();
    }
}