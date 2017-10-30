package org.neogroup.warp.models;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CustomModel {

    private final String modelName;
    private Map<String,Object> properties;

    public CustomModel(String modelName) {
        this.modelName = modelName;
        this.properties = new HashMap<>();
    }

    public String getModelName() {
        return modelName;
    }

    public int size() {
        return properties.size();
    }

    public boolean isEmpty() {
        return properties.isEmpty();
    }

    public boolean contains(String propertyName) {
        return properties.containsKey(propertyName);
    }

    public Object get(Object propertyName) {
        return properties.get(propertyName);
    }

    public Object set(String propertyName, Object value) {
        return properties.put(propertyName, value);
    }

    public Object remove(String propertyName) {
        return properties.remove(propertyName);
    }

    public void clear() {
        properties.clear();
    }

    public Set<String> getPropertyNames() {
        return properties.keySet();
    }

    public Collection<Object> getPropertyValues() {
        return properties.values();
    }
}
