package org.neogroup.warp.utils.json;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class JsonObject extends JsonElement {

    private Map<String, Object> properties;

    public JsonObject() {
        properties = new LinkedHashMap<>();
    }

    public JsonObject set(String name, String value) {
        properties.put(name, value);
        return this;
    }

    public JsonObject set(String name, int value) {
        properties.put(name, value);
        return this;
    }

    public JsonObject set(String name, float value) {
        properties.put(name, value);
        return this;
    }

    public JsonObject set(String name, double value) {
        properties.put(name, value);
        return this;
    }

    public JsonObject set(String name, boolean value) {
        properties.put(name, value);
        return this;
    }

    public JsonObject set(String name, JsonElement value) {
        properties.put(name, value);
        return this;
    }

    public <V> V get(String name) {
        return (V)properties.get(name);
    }

    public boolean has(String key) {
        return properties.containsKey(key);
    }

    public void clear() {
        properties.clear();
    }

    public Set<String> keys() {
        return properties.keySet();
    }

    public int size() {
        return properties.size();
    }
}
