package ru.mipt.bit.platformer.core;

import com.badlogic.gdx.math.GridPoint2;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tank {
    private final float movementSpeed = 0.4f;

    private final GridPoint2 coordinates = new GridPoint2(1, 0);
    private final GridPoint2 playerDestinationCoordinates = new GridPoint2(1, 1);
    private float playerMovementProgress = 1f;
    private float playerRotation;

    public boolean canMoveInThisTick() {
        return isEqual(playerMovementProgress, 1f);
    }

    public void moveUp(GridPoint2 obstacleCoordinate) {
        if (!obstacleCoordinate.equals(incrementedY(coordinates))) {
            playerDestinationCoordinates.y++;
            playerMovementProgress = 0f;
        }
        playerRotation = 90f;
    }

    public void moveLeft(GridPoint2 obstacleCoordinate) {
        if (!obstacleCoordinate.equals(decrementedX(coordinates))) {
            playerDestinationCoordinates.x--;
            playerMovementProgress = 0f;
        }
        playerRotation = -180f;
    }

    public void moveDown(GridPoint2 obstacleCoordinate) {
        if (!obstacleCoordinate.equals(decrementedY(coordinates))) {
            playerDestinationCoordinates.y--;
            playerMovementProgress = 0f;
        }
        playerRotation = -90f;
    }

    public void moveRight(GridPoint2 obstacleCoordinate) {
        if (!obstacleCoordinate.equals(incrementedX(coordinates))) {
            playerDestinationCoordinates.x++;
            playerMovementProgress = 0f;
        }
        playerRotation = 0f;
    }

    public void processMovementProgress(float deltaTime) {
        playerMovementProgress = continueProgress(playerMovementProgress, deltaTime, movementSpeed);
        if (isEqual(playerMovementProgress, 1f)) {
            coordinates.set(playerDestinationCoordinates);
        }
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public GridPoint2 getPlayerDestinationCoordinates() {
        return playerDestinationCoordinates;
    }

    public float getPlayerMovementProgress() {
        return playerMovementProgress;
    }

    public float getPlayerRotation() {
        return playerRotation;
    }
}
