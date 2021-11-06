package org.neogroup.warp.data;

import org.neogroup.warp.utils.formatters.Formatter;
import org.neogroup.warp.utils.formatters.JsonFormatter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class DataObject {

    private Map<String, Object> properties;
    private static Formatter jsonFormatter = new JsonFormatter();

    public DataObject() {
        properties = new LinkedHashMap<>();
    }

    public DataObject set(String key, Object value) {
        properties.put(key, value);
        return this;
    }

    public <V> V get(String key) {
        return (V)properties.get(key);
    }

    public boolean has(String key) {
        return properties.containsKey(key);
    }

    public DataObject remove(String key) {
        properties.remove(key);
        return this;
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

    @Override
    public String toString() {
        return jsonFormatter.format(this);
    }
}
