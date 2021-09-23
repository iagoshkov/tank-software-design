package ru.mipt.bit.platformer.models.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;

public class Tank extends AbstractMovableGameGraphicObject {
    private static final float MOVEMENT_SPEED = 0.4f;

    public Tank(Texture texture, GridPoint2 coordinates, float rotation) {
        super(texture, coordinates, rotation);
    }

    @Override
    public void move(Direction direction, float deltaTime) {
        movementProgress = MIN_PROGRESS;

        destinationCoordinates.x += direction.getShift().x;
        destinationCoordinates.y += direction.getShift().y;

        rotation = direction.getRotation();

        movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);

        if (isStopped()) {
            coordinates.set(destinationCoordinates);
        }

    }

    public boolean isStopped() {
        return movementProgress == MAX_PROGRESS;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public float getMovementProgress() {
        return movementProgress;
    }
}
