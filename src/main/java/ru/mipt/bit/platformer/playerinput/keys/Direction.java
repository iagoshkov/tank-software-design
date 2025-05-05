package ru.mipt.bit.platformer.playerinput.keys;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    RIGHT(0f, new GridPoint2(1, 0)),
    LEFT(-180f, new GridPoint2(-1, 0)),
    UP(90f, new GridPoint2(0, 1)),
    DOWN(-90f, new GridPoint2(0, -1)),
    ;

    private final float directionRotation;
    private final GridPoint2 directionPoint;

    Direction(float directionRotation, GridPoint2 directionPoint) {
        this.directionRotation = directionRotation;
        this.directionPoint = directionPoint;
    }

    public float getDirectionRotation() {
        return directionRotation;
    }

    public GridPoint2 getDirectionPoint() {
        return directionPoint;
    }
}
