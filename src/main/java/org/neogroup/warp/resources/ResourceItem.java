package org.neogroup.warp.resources;

import java.util.HashMap;
import java.util.Map;

public class ResourceItem {

    private final Map<String,Object> fields;

    public ResourceItem() {
        this.fields = new HashMap<>();
    }

    public <V> V get(String fieldName) {
        return (V) fields.get(fieldName);
    }

    public ResourceItem set(String fieldName, Object value) {
        fields.put(fieldName, value);
        return this;
    }

    public Map<String, Object> get() {
        return fields;
    }

    public ResourceItem clear () {
        fields.clear();
        return this;
    }
}