package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    RIGHT(0f, new GridPoint2(1, 0)),
    LEFT(180f, new GridPoint2(-1, 0)),
    UP(90f, new GridPoint2(0, 1)),
    DOWN(-90f, new GridPoint2(0, -1)),
    NULL(0f, new GridPoint2(0, 0));

    private final float rotation;
    private final GridPoint2 directionVector;

    Direction(float rotation, GridPoint2 directionVector) {
        this.rotation = rotation;
        this.directionVector = directionVector;
    }

    public GridPoint2 getNextPosition(GridPoint2 currentPosition) {
        return new GridPoint2(
            currentPosition.x + directionVector.x,
            currentPosition.y + directionVector.y
        );
    }

    public float getRotation() {
        return rotation;
    }

    public GridPoint2 getDirectionVector() {
        return directionVector;
    }

    public static Direction fromRotation(float rotation) {
    // Простая проверка с допуском
    if (Math.abs(rotation - 0) < 1) return RIGHT;
    if (Math.abs(rotation - 180) < 1) return LEFT;
    if (Math.abs(rotation - 90) < 1) return UP;
    if (Math.abs(rotation - 270) < 1 || Math.abs(rotation + 90) < 1) return DOWN;
    
    return RIGHT; // По умолчанию
    }
}