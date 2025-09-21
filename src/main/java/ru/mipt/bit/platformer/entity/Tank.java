package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;

public class Tank {
    private GridPoint2 coordinates;
    private GridPoint2 coordinatesDestination;
    private float rotation;
    private float movementProgress;
    private Texture texture;

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public GridPoint2 getCoordinatesDestination() {
        return coordinatesDestination;
    }

    public float getRotation() {
        return rotation;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setCoordinates(GridPoint2 coordinates) {
        this.coordinates = coordinates;
    }

    public void setCoordinatesDestination(GridPoint2 coordinatesDestination) {
        this.coordinatesDestination = coordinatesDestination;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setMovementProgress(float movementProgress) {
        this.movementProgress = movementProgress;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
