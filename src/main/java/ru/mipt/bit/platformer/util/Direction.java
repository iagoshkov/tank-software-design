package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.Input.Keys.*;

public enum Direction {
    UP(new Vector2(0, 1), 90f, com.badlogic.gdx.Input.Keys.UP, com.badlogic.gdx.Input.Keys.W),
    DOWN(new Vector2(0, -1), -90f, com.badlogic.gdx.Input.Keys.DOWN, com.badlogic.gdx.Input.Keys.S),
    LEFT(new Vector2(-1, 0), -180f, com.badlogic.gdx.Input.Keys.LEFT, com.badlogic.gdx.Input.Keys.A),
    RIGHT(new Vector2(1, 0), 0f, com.badlogic.gdx.Input.Keys.RIGHT, com.badlogic.gdx.Input.Keys.D);

    private final Vector2 direction;
    private final float rotation;
    private final int primaryKey;
    private final int alternativeKey;

    Direction(Vector2 direction, float rotation, int primaryKey, int alternativeKey) {
        this.direction = direction;
        this.rotation = rotation;
        this.primaryKey = primaryKey;
        this.alternativeKey = alternativeKey;
    }

    public GridPoint2 calculateNewPosition(GridPoint2 currentPosition) {
        return new GridPoint2(currentPosition.x + (int) direction.x, currentPosition.y + (int) direction.y);
    }

    public float getRotation() {
        return rotation;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public int getAlternativeKey() {
        return alternativeKey;
    }

    public boolean isKeyPressed() {
        return com.badlogic.gdx.Gdx.input.isKeyPressed(primaryKey) || com.badlogic.gdx.Gdx.input.isKeyPressed(alternativeKey);
    }

    public Vector2 getDirection() {
        return direction;
    }
}