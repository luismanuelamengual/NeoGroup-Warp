package org.neogroup.warp.resources;

import org.neogroup.warp.data.DataObject;

import java.util.ArrayList;
import java.util.Collection;

public class DataResourceProxy extends ResourceProxy<DataObject> {

    public DataResourceProxy(String resourceName, Resource resource) {
        super(resourceName, resource);
    }

    public Collection<DataObject> find() {
        return createDataCollection(super.find());
    }

    public Collection<DataObject> insert () {
        return createDataCollection(super.insert());
    }

    public Collection<DataObject> update () {
        return createDataCollection(super.update());
    }

    public Collection<DataObject> delete () {
        return createDataCollection(super.delete());
    }

    private Collection<DataObject> createDataCollection(Collection collection) {
        Collection<DataObject> dataCollection = null;
        if (collection != null) {
            dataCollection = new ArrayList<>();
        }
        return dataCollection;
    }
}