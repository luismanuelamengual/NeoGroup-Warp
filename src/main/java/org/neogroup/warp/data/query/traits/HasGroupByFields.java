package org.neogroup.warp.data.query.traits;

import org.neogroup.warp.data.query.fields.Field;

import java.util.Collections;
import java.util.List;

public interface HasGroupByFields<R extends HasGroupByFields<R>> {

    List<Field> getGroupByFields();

    R setGroupByFields (List<Field> groupByFields);

    default R groupBy(String... fields) {
        List<Field> groupByFields = getGroupByFields();
        for (String field : fields) {
            groupByFields.add(new Field(field));
        }
        return (R)this;
    }

    default R groupBy(Field... groupByFields) {
        Collections.addAll(getGroupByFields(), groupByFields);
        return (R)this;
    }

    default R clearGroupByFields() {
        getGroupByFields().clear();
        return (R)this;
    }
}