package ru.mipt.bit.platformer.models.graphics;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.GridPoint2;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.badlogic.gdx.Input.Keys.*;

public enum Direction {
    UP(Arrays.asList(W, Input.Keys.UP), new GridPoint2(0, 1), 90f),
    DOWN(Arrays.asList(D, Input.Keys.DOWN), new GridPoint2(0, -1), -90f),
    LEFT(Arrays.asList(L, Input.Keys.LEFT), new GridPoint2(-1, 0), -180f),
    RIGHT(Arrays.asList(R, Input.Keys.RIGHT), new GridPoint2(1, 0),0f);

    private final List<Integer> keys;
    private final GridPoint2 shift;
    private final float rotation;

    Direction(List<Integer> keys, GridPoint2 shift, float rotation) {
        this.keys = keys;
        this.shift = shift;
        this.rotation = rotation;
    }

    public static Direction getCalledDirection(Input input) {
        Optional<Direction> called = Arrays.stream(Direction.values()).filter(direction -> {
            for (int key : direction.keys) {
                return input.isKeyPressed(key);
            }
            return false;
        }).findAny();

        return called.orElseGet(called::get);
    }

    public List<Integer> getKeys() {
        return keys;
    }

    public GridPoint2 getShift() {
        return shift;
    }

    public float getRotation() {
        return rotation;
    }
}
