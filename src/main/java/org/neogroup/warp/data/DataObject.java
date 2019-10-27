package org.neogroup.warp.data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class DataObject {

    private Map<String, Object> properties;

    public DataObject() {
        properties = new LinkedHashMap<>();
    }

    public DataObject set(String name, Object value) {
        properties.put(name, value);
        return this;
    }

    public <V> V get(String name) {
        return (V)properties.get(name);
    }

    public boolean has(String key) {
        return properties.containsKey(key);
    }

    public DataObject clear() {
        properties.clear();
        return this;
    }

    public Set<String> properties() {
        return properties.keySet();
    }

    public int size() {
        return properties.size();
    }
}
