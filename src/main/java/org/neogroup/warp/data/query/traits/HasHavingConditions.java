package org.neogroup.warp.data.query.traits;

import org.neogroup.warp.data.query.conditions.*;
import org.neogroup.warp.data.query.fields.Field;

import java.util.Collection;

public interface HasHavingConditions<R extends HasHavingConditions<R>> {

    ConditionGroup getHavingConditions();

    R setHavingConditions (ConditionGroup conditionGroup);

    default ConditionGroupConnector getHavingConnector() {
        return getHavingConditions().getConnector();
    }

    default R setHavingConnector(ConditionGroupConnector connector) {
        getHavingConditions().setConnector(connector);
        return (R)this;
    }

    default R clearHavingConditions() {
        getHavingConditions().clearConditions();
        return (R)this;
    }

    default boolean hasHavingConditions() {
        return getHavingConditions().isEmpty();
    }

    default R having(Condition condition) {
        getHavingConditions().addCondition(condition);
        return (R)this;
    }

    default R having(String field, Object value) {
        getHavingConditions().on(field, value);
        return (R)this;
    }

    default R having(Field field, Object value) {
        getHavingConditions().on(field, value);
        return (R)this;
    }

    default R having(String field, ConditionOperator operator, Object value) {
        getHavingConditions().on(field, operator, value);
        return (R)this;
    }

    default R having(Field field, ConditionOperator operator, Object value) {
        getHavingConditions().on(field, operator, value);
        return (R)this;
    }

    default R havingGroup(ConditionGroup condition) {
        getHavingConditions().onGroup(condition);
        return (R)this;
    }

    default R havingRaw(RawCondition condition) {
        getHavingConditions().onRaw(condition);
        return (R)this;
    }

    default R havingField(String field1, String field2) {
        getHavingConditions().onField(field1, field2);
        return (R)this;
    }

    default R havingField(Field field1, Field field2) {
        getHavingConditions().onField(field1, field2);
        return (R)this;
    }

    default R havingField(String field1, ConditionOperator operator, String field2) {
        getHavingConditions().onField(field1, operator, field2);
        return (R)this;
    }

    default R havingField(Field field1, ConditionOperator operator, Field field2) {
        getHavingConditions().onField(field1, operator, field2);
        return (R)this;
    }

    default R havingNull(String field) {
        getHavingConditions().onNull(field);
        return (R)this;
    }

    default R havingNull(Field field) {
        getHavingConditions().onNull(field);
        return (R)this;
    }

    default R havingNotNull(String field) {
        getHavingConditions().onNotNull(field);
        return (R)this;
    }

    default R havingNotNull(Field field) {
        getHavingConditions().onNotNull(field);
        return (R)this;
    }

    default R havingIn(String field, Collection<Object> values) {
        getHavingConditions().onIn(field, values);
        return (R)this;
    }

    default R havingIn(Field field, Collection<Object> values) {
        getHavingConditions().onIn(field, values);
        return (R)this;
    }

    default R havingNotIn(String field, Collection<Object> values) {
        getHavingConditions().onNotIn(field, values);
        return (R)this;
    }

    default R havingNotIn(Field field, Collection<Object> values) {
        getHavingConditions().onNotIn(field, values);
        return (R)this;
    }

    default R havingContains(String field, Object value) {
        getHavingConditions().onContains(field, value);
        return (R)this;
    }

    default R havingContains(Field field, Object value) {
        getHavingConditions().onContains(field, value);
        return (R)this;
    }

    default R havingNotContains(String field, Object value) {
        getHavingConditions().onNotContains(field, value);
        return (R)this;
    }

    default R havingNotContains(Field field, Object value) {
        getHavingConditions().onNotContains(field, value);
        return (R)this;
    }

    default R havingGreaterThan(String field, Object value) {
        getHavingConditions().onGreaterThan(field, value);
        return (R)this;
    }

    default R havingGreaterThan(Field field, Object value) {
        getHavingConditions().onGreaterThan(field, value);
        return (R)this;
    }

    default R havingGreaterOrEqualsThan(String field, Object value) {
        getHavingConditions().onGreaterOrEqualsThan(field, value);
        return (R)this;
    }

    default R havingGreaterOrEqualsThan(Field field, Object value) {
        getHavingConditions().onGreaterOrEqualsThan(field, value);
        return (R)this;
    }

    default R havingLowerThan(String field, Object value) {
        getHavingConditions().onLowerThan(field, value);
        return (R)this;
    }

    default R havingLowerThan(Field field, Object value) {
        getHavingConditions().onLowerThan(field, value);
        return (R)this;
    }

    default R havingLowerOrEqualsThan(String field, Object value) {
        getHavingConditions().onLowerOrEqualsThan(field, value);
        return (R)this;
    }

    default R havingLowerOrEqualsThan(Field field, Object value) {
        getHavingConditions().onLowerOrEqualsThan(field, value);
        return (R)this;
    }
}
