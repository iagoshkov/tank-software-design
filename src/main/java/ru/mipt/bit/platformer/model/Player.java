package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public class Player {
    private GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private float rotation;

    public Player(GridPoint2 startCoordinates) {
        this.coordinates = new GridPoint2(startCoordinates);
        this.destinationCoordinates = new GridPoint2(startCoordinates);
        this.rotation = 0f;
    }

    public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates);
    }

    public GridPoint2 getDestinationCoordinates() {
        return new GridPoint2(destinationCoordinates);
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public float getRotation() {
        return rotation;
    }

    public boolean isMoving() {
        return movementProgress < 1f;
    }

    public void setDestination(GridPoint2 destination) {
        this.destinationCoordinates.set(destination);
        this.movementProgress = 0f;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void updateProgress(float deltaTime, float movementSpeed) {
        if (movementProgress < 1f) {
            movementProgress = Math.min(1f, movementProgress + deltaTime / movementSpeed);
            if (movementProgress >= 1f) {
                coordinates.set(destinationCoordinates);
            }
        }
    }
}
