package org.neogroup.warp.data;

/**
 *
 */
public class SelectField {

    private final String field;
    private final String alias;

    /**
     *
     * @param field
     */
    public SelectField(String field) {
        this(field, null);
    }

    /**
     *
     * @param field
     * @param alias
     */
    public SelectField(String field, String alias) {
        this.field = field;
        this.alias = alias;
    }

    /**
     *
     * @return
     */
    public String getField() {
        return field;
    }

    /**
     *
     * @return
     */
    public String getAlias() {
        return alias;
    }
}
