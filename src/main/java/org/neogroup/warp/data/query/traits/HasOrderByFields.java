package org.neogroup.warp.data.query.traits;

import org.neogroup.warp.data.query.fields.SortDirection;
import org.neogroup.warp.data.query.fields.SortField;

import java.util.Collections;
import java.util.List;

public interface HasOrderByFields<R> {

    List<SortField> getOrderByFields();

    R setOrderByFields (List<SortField> orderByFields);

    default R orderBy(String... fields) {
        List<SortField> orderByFields = getOrderByFields();
        for (String field : fields) {
            orderByFields.add(new SortField(field));
        }
        return (R)this;
    }

    default R orderBy(SortField... orderByFields) {
        Collections.addAll(getOrderByFields(), orderByFields);
        return (R)this;
    }

    default R orderBy(String field, SortDirection direction) {
        return orderBy(new SortField(field, direction));
    }

    default R clearOrderByFields() {
        getOrderByFields().clear();
        return (R)this;
    }
}
