package org.neogroup.warp.data.query.traits;

import org.neogroup.warp.data.query.fields.SelectField;

import java.util.Collections;
import java.util.List;

public interface HasSelectFields<R> {

    List<SelectField> getSelectFields();

    R setSelectFields (List<SelectField> selectFields);

    default R select(String... fields) {
        List<SelectField> selectFields = getSelectFields();
        for (String field : fields) {
            selectFields.add(new SelectField(field));
        }
        return (R)this;
    }

    default R select(SelectField... fields) {
        Collections.addAll(getSelectFields(), fields);
        return (R)this;
    }

    default R selectField (SelectField field) {
        getSelectFields().add (field);
        return (R)this;
    }

    default R selectField (String name) {
        return selectField(name, null);
    }

    default R selectField (String name, String alias) {
        return selectField(null, name, alias);
    }

    default R selectField (String tableName, String name, String alias) {
        return selectField(new SelectField(tableName, name, alias));
    }

    default R clearSelectFields () {
        getSelectFields().clear();
        return (R)this;
    }
}