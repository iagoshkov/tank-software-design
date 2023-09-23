package ru.mipt.bit.platformer.Entities;

import com.badlogic.gdx.math.GridPoint2;

public interface MapObject {
    GridPoint2 getCoordinates();
    default float getRotation() {
        return 0f;
    }
}
