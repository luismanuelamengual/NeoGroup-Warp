package org.neogroup.warp.data;

/**
 *
 */
public class OrderByField {

    private final String field;
    private final Direction direction;

    /**
     *
     * @param field
     */
    public OrderByField(String field) {
        this(field, Direction.ASC);
    }

    /**
     *
     * @param field
     * @param direction
     */
    public OrderByField(String field, Direction direction) {
        this.field = field;
        this.direction = direction;
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
    public Direction getDirection() {
        return direction;
    }

    /**
     *
     */
    public enum Direction {
        ASC,
        DESC
    }
}
