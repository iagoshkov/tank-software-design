package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

public class ANGLE {
    private final GridPoint2 coordinates;
    private final float angle;

    public ANGLE(int x, int y) {
        coordinates = new GridPoint2(x, y);
        angle = (float) (Math.atan2(y, x) * 180 / Math.PI);
    }

    public float ANGLE() {
        return angle;
    }

    public GridPoint2 Coordinates() {
        return coordinates;
    }
}