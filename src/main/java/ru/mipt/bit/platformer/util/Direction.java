package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

import static com.badlogic.gdx.Input.Keys.*;

public enum Direction {
    UP(90f, com.badlogic.gdx.Input.Keys.UP, com.badlogic.gdx.Input.Keys.W) {
        @Override
        public GridPoint2 calculateNewPosition(GridPoint2 currentPosition) {
            return GdxGameUtils.incrementedY(currentPosition);
        }
    },

    DOWN(-90f, com.badlogic.gdx.Input.Keys.DOWN, com.badlogic.gdx.Input.Keys.S) {
        @Override
        public GridPoint2 calculateNewPosition(GridPoint2 currentPosition) {
            return GdxGameUtils.decrementedY(currentPosition);
        }
    },

    LEFT(-180f, com.badlogic.gdx.Input.Keys.LEFT, com.badlogic.gdx.Input.Keys.A) {
        @Override
        public GridPoint2 calculateNewPosition(GridPoint2 currentPosition) {
            return GdxGameUtils.decrementedX(currentPosition);
        }
    },

    RIGHT(0f, com.badlogic.gdx.Input.Keys.RIGHT, com.badlogic.gdx.Input.Keys.D) {
        @Override
        public GridPoint2 calculateNewPosition(GridPoint2 currentPosition) {
            return GdxGameUtils.incrementedX(currentPosition);
        }
    };

    private final float rotation;
    private final int primaryKey;
    private final int alternativeKey;

    Direction(float rotation, int primaryKey, int alternativeKey) {
        this.rotation = rotation;
        this.primaryKey = primaryKey;
        this.alternativeKey = alternativeKey;
    }

    public abstract GridPoint2 calculateNewPosition(GridPoint2 currentPosition);

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
}