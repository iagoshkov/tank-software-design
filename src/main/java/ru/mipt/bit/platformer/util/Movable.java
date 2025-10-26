package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

public interface Movable {
    boolean canMoveTo(GridPoint2 newPosition);
    void moveTo(GridPoint2 newPosition, Direction direction);
    boolean isMovementComplete();
    GridPoint2 getCoordinates();
}