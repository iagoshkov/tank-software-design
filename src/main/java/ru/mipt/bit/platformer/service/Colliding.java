package ru.mipt.bit.platformer.service;

import com.badlogic.gdx.math.GridPoint2;

public interface Colliding {
    boolean isCollisionPossible(GridPoint2 othersCoordinates);
}
