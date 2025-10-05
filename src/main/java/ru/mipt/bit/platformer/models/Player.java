package ru.mipt.bit.platformer.models;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class Player extends RigidBody {

    private static final float MOVEMENT_SPEED = 0.4f;

    private final TileMovement tileMovement;
    private final GridPoint2 previousCoordinates;
    private final GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;

    private final Rectangle rectangle;

    public Player(TileMovement tileMovement, GridPoint2 startCoordinates) {
        super(startCoordinates);
        this.tileMovement = tileMovement;
        this.previousCoordinates = new GridPoint2(startCoordinates);
        this.destinationCoordinates = new GridPoint2(startCoordinates);
        this.rectangle = new Rectangle();

        tileMovement.moveRectangleToTileCenter(rectangle, startCoordinates);
    }

    @Override
    public void update(float deltaTime) {
        if (movementProgress < 1f) {
            movementProgress = Math.min(1f, movementProgress + deltaTime / MOVEMENT_SPEED);
            if (isEqual(movementProgress, 1f)) {
                coordinates.set(destinationCoordinates);
            }
        }

        tileMovement.moveRectangleBetweenTileCenters(
                rectangle,
                previousCoordinates,
                destinationCoordinates,
                movementProgress
        );
    }

    public void startMovement(GridPoint2 newDestination, float rotation) {
        if (isMoving()) return;
        previousCoordinates.set(coordinates);
        destinationCoordinates.set(newDestination);
        this.rotation = rotation;
        this.movementProgress = 0f;
    }

    public boolean isMoving() {
        return movementProgress < 1f;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public float getRotation() {
        return rotation;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }
}
