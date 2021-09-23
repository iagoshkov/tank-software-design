package ru.mipt.bit.platformer;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.GridPoint2;

import java.util.Map;

public enum Direction {
    RIGHT,
    UP,
    LEFT,
    DOWN;

    public static final Map<Direction, Float> ANGLES;
    public static final Map<Direction, GridPoint2> COORDINATE_DELTAS;

    static {
        ANGLES = Map.of(
                RIGHT, 0f,
                UP, 90f,
                LEFT, -180f,
                DOWN, -90f);

        COORDINATE_DELTAS = Map.of(
                RIGHT, new GridPoint2(1, 0),
                UP, new GridPoint2(0, 1),
                LEFT, new GridPoint2(-1, 0),
                DOWN, new GridPoint2(0, -1)
        );
    }

    public float calcAngle() {
        return ANGLES.get(this);
    }

    public GridPoint2 calcDestinationCoordinatesFrom(GridPoint2 point) {
        var destinationCoordinate = new GridPoint2(point.x, point.y);
        return destinationCoordinate.add(COORDINATE_DELTAS.get(this));
    }

    public static Direction fromKey(int key) {
        if (key == Keys.RIGHT || key == Keys.D) {
            return RIGHT;
        }
        if (key == Keys.UP || key == Keys.W) {
            return UP;
        }
        if (key == Keys.LEFT || key == Keys.A) {
            return LEFT;
        }
        if (key == Keys.DOWN || key == Keys.S) {
            return DOWN;
        }
        throw new IllegalArgumentException();
    }
}
