package org.neogroup.warp.resources;

import org.neogroup.warp.data.DataObject;
import org.neogroup.warp.utils.Introspection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenericResourceProxy extends ResourceProxy<DataObject> {

    public GenericResourceProxy(String resourceName, Resource resource) {
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
        List<DataObject> dataCollection = null;
        if (collection != null) {
            dataCollection = new ArrayList<>();
            for (Object object : collection) {
                DataObject dataObject = new DataObject();
                List<Introspection.Property> properties = Introspection.getProperties(object.getClass());
                for (Introspection.Property property : properties) {
                    dataObject.set(property.getName(), property.getValue(object));
                }
                dataCollection.add(dataObject);
            }
        }
        return dataCollection;
    }
}