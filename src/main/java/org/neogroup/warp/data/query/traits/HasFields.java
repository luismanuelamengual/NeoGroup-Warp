package org.neogroup.warp.data.query.traits;

import java.util.Map;

public interface HasFields<R> {

    Map<String, Object> getFields();

    R setFields (Map<String, Object> fields);

    default R set (String field, Object value) {
        getFields().put(field, value);
        return (R)this;
    }

    default <V extends Object> V get (String field) {
        return (V)getFields().get(field);
    }

    default R clearFields () {
        getFields().clear();
        return (R)this;
    }
}