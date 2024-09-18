package ru.mipt.bit.platformer.game.player;

import ru.mipt.bit.platformer.game.level.Point;

public enum Direction {
    UP(new Point(0, 1), 90f),
    DOWN(new Point(0, -1), -90f),
    LEFT(new Point(-1, 0), -180f),
    RIGHT(new Point(1, 0), 0f);

    private final Point directionChange;
    private final float rotation;

    Direction(Point direction, float rotation) {
        this.directionChange = direction;
        this.rotation = rotation;
    }

    public float getRotation() {
        return rotation;
    }

    public int getShiftX() {
        return directionChange.x;
    }

    public int getShiftY() {
        return directionChange.y;
    }
}
