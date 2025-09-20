package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

public class Tank extends GameObject {
    private GridPoint2 destinationCoordinates;
    private float movementProgress;
    private final TileMovement tileMovement;
    private final float movementSpeed;

    public Tank(GridPoint2 coordinates, TextureRegion graphics, TiledMapTileLayer groundLayer,
                TileMovement tileMovement, float movementSpeed) {
        super(coordinates, graphics, groundLayer);
        this.destinationCoordinates = new GridPoint2(coordinates);
        this.movementProgress = 1f;
        this.tileMovement = tileMovement;
        this.movementSpeed = movementSpeed;
    }

    @Override
    public void update(float deltaTime) {
        movementProgress = GdxGameUtils.continueProgress(movementProgress, deltaTime, movementSpeed);
        tileMovement.moveRectangleBetweenTileCenters(rectangle, coordinates, destinationCoordinates, movementProgress);

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