package org.neogroup.warp.data.query.fields;

public class OrderByField {

    private final Field field;
    private final Direction direction;

    public OrderByField (String rawField) {
        this(new RawField(rawField));
    }

    public OrderByField (String rawField, Direction direction) {
        this(new RawField(rawField), direction);
    }

    public OrderByField (Field field) {
        this(field, Direction.ASC);
    }

    public OrderByField (Field field, Direction direction) {
        this.field = field;
        this.direction = direction;
    }

    public Field getField() {
        return field;
    }

    public Direction getDirection() {
        return direction;
    }

    public enum Direction {
        ASC,
        DESC
    }
}
