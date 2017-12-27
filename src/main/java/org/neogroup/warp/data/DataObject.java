package org.neogroup.warp.data;

import java.util.HashMap;
import java.util.Map;

public class DataObject {

    private final Map<String,Object> properties;

    public DataObject() {
        this.properties = new HashMap<>();
    }

    public <V> V get(String propertyName) {
        return (V) properties.get(propertyName);
    }

    public void set(String propertyName, Object value) {
        properties.put(propertyName, value);
    }
}
