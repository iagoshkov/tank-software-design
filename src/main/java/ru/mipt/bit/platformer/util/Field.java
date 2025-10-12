package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

public interface Field {
    boolean isPositionOccupied(GridPoint2 position);
}