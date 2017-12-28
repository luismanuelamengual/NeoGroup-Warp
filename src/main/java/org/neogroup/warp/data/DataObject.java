package org.neogroup.warp.data;

import java.util.HashMap;
import java.util.Map;

public class DataObject {

    private final Map<String,Object> fields;

    public DataObject() {
        this.fields = new HashMap<>();
    }

    public <V> V getField(String fieldName) {
        return (V) fields.get(fieldName);
    }

    public void setField(String fieldName, Object value) {
        fields.put(fieldName, value);
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void clearFields () {
        fields.clear();
    }
}
