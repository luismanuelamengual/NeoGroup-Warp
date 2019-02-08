package org.neogroup.warp.data.query.traits;

import org.neogroup.warp.data.query.conditions.*;
import org.neogroup.warp.data.query.fields.Field;

import java.util.Collection;

public interface HasWhereConditions<R extends HasWhereConditions<R>> {

    ConditionGroup getWhereConditions();

    R setWhereConditions (ConditionGroup conditionGroup);

    default ConditionGroupConnector getWhereConnector() {
        return getWhereConditions().getConnector();
    }

    default R setWhereConnector(ConditionGroupConnector connector) {
        getWhereConditions().setConnector(connector);
        return (R)this;
    }

    default R clearWhereConditions() {
        getWhereConditions().clearConditions();
        return (R)this;
    }

    default boolean hasWhereConditions() {
        return getWhereConditions().isEmpty();
    }

    default R where(Condition condition) {
        getWhereConditions().addCondition(condition);
        return (R)this;
    }

    default R where(String field, Object value) {
        getWhereConditions().on(field, value);
        return (R)this;
    }

    default R where(Field field, Object value) {
        getWhereConditions().on(field, value);
        return (R)this;
    }

    default R where(String field, ConditionOperator operator, Object value) {
        getWhereConditions().on(field, operator, value);
        return (R)this;
    }

    default R where(Field field, ConditionOperator operator, Object value) {
        getWhereConditions().on(field, operator, value);
        return (R)this;
    }

    default R whereGroup(ConditionGroup condition) {
        getWhereConditions().onGroup(condition);
        return (R)this;
    }

    default R whereRaw(RawCondition condition) {
        getWhereConditions().onRaw(condition);
        return (R)this;
    }

    default R whereField(String field1, String field2) {
        getWhereConditions().onField(field1, field2);
        return (R)this;
    }

    default R whereField(Field field1, Field field2) {
        getWhereConditions().onField(field1, field2);
        return (R)this;
    }

    default R whereField(String field1, ConditionOperator operator, String field2) {
        getWhereConditions().onField(field1, operator, field2);
        return (R)this;
    }

    default R whereField(Field field1, ConditionOperator operator, Field field2) {
        getWhereConditions().onField(field1, operator, field2);
        return (R)this;
    }

    default R whereNull(String field) {
        getWhereConditions().onNull(field);
        return (R)this;
    }

    default R whereNull(Field field) {
        getWhereConditions().onNull(field);
        return (R)this;
    }

    default R whereNotNull(String field) {
        getWhereConditions().onNotNull(field);
        return (R)this;
    }

    default R whereNotNull(Field field) {
        getWhereConditions().onNotNull(field);
        return (R)this;
    }

    default R whereIn(String field, Collection<Object> values) {
        getWhereConditions().onIn(field, values);
        return (R)this;
    }

    default R whereIn(Field field, Collection<Object> values) {
        getWhereConditions().onIn(field, values);
        return (R)this;
    }

    default R whereNotIn(String field, Collection<Object> values) {
        getWhereConditions().onNotIn(field, values);
        return (R)this;
    }

    default R whereNotIn(Field field, Collection<Object> values) {
        getWhereConditions().onNotIn(field, values);
        return (R)this;
    }

    default R whereContains(String field, Object value) {
        getWhereConditions().onContains(field, value);
        return (R)this;
    }

    default R whereContains(Field field, Object value) {
        getWhereConditions().onContains(field, value);
        return (R)this;
    }

    default R whereNotContains(String field, Object value) {
        getWhereConditions().onNotContains(field, value);
        return (R)this;
    }

    default R whereNotContains(Field field, Object value) {
        getWhereConditions().onNotContains(field, value);
        return (R)this;
    }

    default R whereGreaterThan(String field, Object value) {
        getWhereConditions().onGreaterThan(field, value);
        return (R)this;
    }

    default R whereGreaterThan(Field field, Object value) {
        getWhereConditions().onGreaterThan(field, value);
        return (R)this;
    }

    default R whereGreaterOrEqualsThan(String field, Object value) {
        getWhereConditions().onGreaterOrEqualsThan(field, value);
        return (R)this;
    }

    default R whereGreaterOrEqualsThan(Field field, Object value) {
        getWhereConditions().onGreaterOrEqualsThan(field, value);
        return (R)this;
    }

    default R whereLowerThan(String field, Object value) {
        getWhereConditions().onLowerThan(field, value);
        return (R)this;
    }

    default R whereLowerThan(Field field, Object value) {
        getWhereConditions().onLowerThan(field, value);
        return (R)this;
    }

    default R whereLowerOrEqualsThan(String field, Object value) {
        getWhereConditions().onLowerOrEqualsThan(field, value);
        return (R)this;
    }

    default R whereLowerOrEqualsThan(Field field, Object value) {
        getWhereConditions().onLowerOrEqualsThan(field, value);
        return (R)this;
    }
}
