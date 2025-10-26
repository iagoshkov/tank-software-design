package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

public interface PositionOccupier {
    boolean occupiesPosition(GridPoint2 position);
}