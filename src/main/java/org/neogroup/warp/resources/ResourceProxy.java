package org.neogroup.warp.resources;

import org.neogroup.warp.query.Query;

import java.util.Collection;

public class ResourceProxy<T extends Object> extends Query {

    private Resource<T> resource;

    public ResourceProxy(String resourceName, Resource<T> resource) {
        super(resourceName);
        this.resource = resource;
    }

    public Collection<T> find() {
        return resource.find(this);
    }

    public T insert () {
        return resource.insert(this);
    }

    public T update () {
        return resource.update(this);
    }

    public T delete () {
        return resource.delete(this);
    }

    public T first () {
        limit(1);
        return find().iterator().next();
    }
}