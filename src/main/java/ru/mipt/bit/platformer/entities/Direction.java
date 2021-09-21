package ru.mipt.bit.platformer.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.GridPoint2;

import java.util.Set;

import static com.badlogic.gdx.Input.Keys.*;

public enum Direction {
    UP(Set.of(W, Input.Keys.UP), new GridPoint2(0, 1), 90f),
    DOWN(Set.of(S,Input.Keys.DOWN), new GridPoint2(0, -1), -90f),
    LEFT(Set.of(A,Input.Keys.LEFT), new GridPoint2(-1, 0), -180f),
    RIGHT(Set.of(D,Input.Keys.RIGHT), new GridPoint2(1, 0), 0f);

    Direction(Set<Integer> triggerKeys, GridPoint2 shift, float rotation) {
        this.triggerKeys = triggerKeys;
        this.shift = shift;
        this.rotation = rotation;
    }

    private final Set<Integer> triggerKeys;
    private final GridPoint2 shift;
    private final float rotation;

    public boolean isTriggered(Input input) {
        for (Integer triggerKey : triggerKeys) {
            if (input.isKeyPressed(triggerKey)) {
                return true;
            }
        }
        return false;
    }

    public GridPoint2 getShift() {
        return shift;
    }

    public float getRotation() {
        return rotation;
    }
}
