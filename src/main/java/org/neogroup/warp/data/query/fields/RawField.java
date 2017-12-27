package org.neogroup.warp.data.query.fields;

public class RawField extends Field {

    private final String value;

    public RawField(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
