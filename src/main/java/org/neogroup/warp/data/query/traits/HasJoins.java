package org.neogroup.warp.data.query.traits;

import org.neogroup.warp.data.query.fields.Field;
import org.neogroup.warp.data.query.joins.Join;
import org.neogroup.warp.data.query.joins.JoinType;

import java.util.List;

public interface HasJoins<R> {

    List<Join> getJoins ();

    R setJoins (List<Join> joins);

    default R join (Join join) {
        getJoins().add(join);
        return (R)this;
    }

    default R join (String tableName, String field1, String field2) {
        Join join = new Join(tableName, JoinType.JOIN);
        join.onField(field1, field2);
        return join(join);
    }

    default R join (String tableName, Field field1, Field field2) {
        Join join = new Join(tableName, JoinType.JOIN);
        join.onField(field1, field2);
        return join(join);
    }

    default R innerJoin (String tableName, String field1, String field2) {
        Join join = new Join(tableName, JoinType.INNER_JOIN);
        join.onField(field1, field2);
        return join(join);
    }

    default R innerJoin (String tableName, Field field1, Field field2) {
        Join join = new Join(tableName, JoinType.INNER_JOIN);
        join.onField(field1, field2);
        return join(join);
    }

    default R leftJoin (String tableName, String field1, String field2) {
        Join join = new Join(tableName, JoinType.LEFT_JOIN);
        join.onField(field1, field2);
        return join(join);
    }

    default R leftJoin (String tableName, Field field1, Field field2) {
        Join join = new Join(tableName, JoinType.LEFT_JOIN);
        join.onField(field1, field2);
        return join(join);
    }

    default R rightJoin (String tableName, String field1, String field2) {
        Join join = new Join(tableName, JoinType.RIGHT_JOIN);
        join.onField(field1, field2);
        return join(join);
    }

    default R rightJoin (String tableName, Field field1, Field field2) {
        Join join = new Join(tableName, JoinType.RIGHT_JOIN);
        join.onField(field1, field2);
        return join(join);
    }
}
