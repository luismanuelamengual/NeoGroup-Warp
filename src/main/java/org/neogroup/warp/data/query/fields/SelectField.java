package org.neogroup.warp.data.query.fields;

public class SelectField {

    private final Field field;
    private final String alias;

    public SelectField (String rawField) {
        this(new RawField(rawField));
    }

    public SelectField (String rawField, String alias) {
        this(new RawField(rawField), alias);
    }

    public SelectField(Field field) {
        this(field, null);
    }

    public SelectField(Field field, String alias) {
        this.field = field;
        this.alias = alias;
    }

    public Field getField() {
        return field;
    }

    public String getAlias() {
        return alias;
    }
}
