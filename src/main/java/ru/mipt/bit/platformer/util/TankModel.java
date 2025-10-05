package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

public class TankModel extends GameObjectModel {
    private GridPoint2 destinationCoordinates;
    private float movementProgress;
    private final float movementSpeed;

    public TankModel(GridPoint2 coordinates, float movementSpeed) {
        super(coordinates);
        this.destinationCoordinates = new GridPoint2(coordinates);
        this.movementProgress = 1f;
        this.movementSpeed = movementSpeed;
    }

    @Override
    public void update(float deltaTime) {
        movementProgress = GdxGameUtils.continueProgress(movementProgress, deltaTime, movementSpeed);
        if (GdxGameUtils.isEqual(movementProgress, 1f)) {
            coordinates.set(destinationCoordinates);
        }
    }

    public boolean canMoveTo(GridPoint2 newPosition) {
        return !newPosition.equals(destinationCoordinates) || GdxGameUtils.isEqual(movementProgress, 1f);
    }

    public void moveTo(GridPoint2 newPosition, Direction direction) {
        if (GdxGameUtils.isEqual(movementProgress, 1f)) {
            this.destinationCoordinates.set(newPosition);
            this.movementProgress = 0f;
            this.rotation = direction.getRotation();
        }
    }

    public boolean isMovementComplete() {
        return GdxGameUtils.isEqual(movementProgress, 1f);
    }

    public GridPoint2 getDestinationCoordinates() {
        return new GridPoint2(destinationCoordinates);
    }

    public float getMovementProgress() {
        return movementProgress;
    }
}