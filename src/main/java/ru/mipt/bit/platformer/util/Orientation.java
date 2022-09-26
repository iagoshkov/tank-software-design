package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

public enum Orientation {
    UP(90f, new GridPoint2(0 ,1)),
    DOWN(-90f, new GridPoint2(0 ,-1)),
    LEFT(-180f, new GridPoint2(-1 ,0)),
    RIGHT(0f, new GridPoint2(1 ,0)),
    ;

    private final float angle;
    private final GridPoint2 offset;
    Orientation(float angle, GridPoint2 offset) {
        this.angle = angle;
        this.offset = offset;
    }
    public float toFloat() {
        return this.angle;
    }

    public GridPoint2 addOffsetTo (GridPoint2 coordinates) {
        return new GridPoint2(coordinates.x + this.offset.x, coordinates.y + this.offset.y);
    }
}
