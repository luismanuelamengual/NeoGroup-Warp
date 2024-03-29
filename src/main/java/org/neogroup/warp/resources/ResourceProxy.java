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

    public Collection<T> insert () {
        return resource.insert(createInsertQuery());
    }

    public Collection<T> update () {
        return resource.update(createUpdateQuery());
    }

    public Collection<T> delete () {
        return resource.delete(createDeleteQuery());
    }

    public T first () {
        Collection<T> resources = limit(1).find();
        return !resources.isEmpty()? resources.iterator().next() : null;
    }
}