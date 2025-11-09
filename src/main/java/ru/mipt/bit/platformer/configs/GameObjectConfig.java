package ru.mipt.bit.platformer.configs;

import com.badlogic.gdx.math.GridPoint2;

public abstract class GameObjectConfig {
    private final String texturePath;
    private final GridPoint2 initialPosition;
    private final float movementSpeed;

    public GameObjectConfig(String texturePath, GridPoint2 initialPosition, float movementSpeed) {
        this.texturePath = texturePath;
        this.initialPosition = initialPosition;
        this.movementSpeed = movementSpeed;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public GridPoint2 getInitialPosition() {
        return initialPosition;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }
}