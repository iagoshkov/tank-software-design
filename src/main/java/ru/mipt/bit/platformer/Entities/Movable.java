package ru.mipt.bit.platformer.Entities;

import com.badlogic.gdx.math.GridPoint2;

public interface Movable extends MapObject {
    GridPoint2 getDestinationCoordinates();
    float getMovementProgress();
}
