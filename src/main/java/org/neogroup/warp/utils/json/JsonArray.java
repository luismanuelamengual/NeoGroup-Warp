package org.neogroup.warp.utils.json;

import java.util.ArrayList;
import java.util.List;

public class JsonArray extends JsonElement {

    private List<Object> elements;

    public JsonArray() {
        elements = new ArrayList<>();
    }

    public JsonArray add(String value) {
        elements.add(value);
        return this;
    }

    public JsonArray add(int value) {
        elements.add(value);
        return this;
    }

    public JsonArray add(float value) {
        elements.add(value);
        return this;
    }

    public JsonArray add(double value) {
        elements.add(value);
        return this;
    }

    public JsonArray add(boolean value) {
        elements.add(value);
        return this;
    }

    public JsonArray add(JsonElement value) {
        elements.add(value);
        return this;
    }

    public List<Object> getElements() {
        return elements;
    }

    public int size() {
        return elements.size();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public void clear() {
        elements.clear();
    }
}
