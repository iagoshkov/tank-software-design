package ru.mipt.bit.platformer.abstractions;

import com.badlogic.gdx.math.GridPoint2;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tank {
    private final float movementSpeed = 0.4f;
    private float playerMovementProgress = 1f;
    private float playerRotation;
    
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private GridPoint2 playerCoordinates = new GridPoint2(0, 1);
    // which tile the player want to go next
    private GridPoint2 playerDestinationCoordinates = new GridPoint2(1, 1);


    public float getPlayerMovementProgress() {
        return playerMovementProgress;
    }

    public float getPlayerRotation() {
        return playerRotation;
    }

    public GridPoint2 getPlayerCoordinates() {
        return playerCoordinates;
    }

    public GridPoint2 getPlayerDestinationCoordinates() {
        return playerDestinationCoordinates;
    }

    public boolean isMoving() {
        return isEqual(playerMovementProgress, 1f);
    }

    public void moveUp(GridPoint2 objectCoordinate) {
        if (!objectCoordinate.equals(incrementedY(playerCoordinates))) {
            playerDestinationCoordinates.y++;
            playerMovementProgress = 0f;
        }
        playerRotation = 90f;
    }

    public void moveLeft(GridPoint2 objectCoordinate) {
        if (!objectCoordinate.equals(decrementedX(playerCoordinates))) {
            playerDestinationCoordinates.x--;
            playerMovementProgress = 0f;
        }
        playerRotation = -180f;
    }

    public void moveDown(GridPoint2 objectCoordinate) {
        if (!objectCoordinate.equals(decrementedY(playerCoordinates))) {
            playerDestinationCoordinates.y--;
            playerMovementProgress = 0f;
        }
        playerRotation = -90f;
    }

    public void moveRight(GridPoint2 objectCoordinate) {
        if (!objectCoordinate.equals(incrementedX(playerCoordinates))) {
            playerDestinationCoordinates.x++;
            playerMovementProgress = 0f;
        }
        playerRotation = 0f;
    }

    public void processMovementProgress(float deltaTime) {
        playerMovementProgress = continueProgress(playerMovementProgress, deltaTime, movementSpeed);
        if (isEqual(playerMovementProgress, 1f)) {
            // record that the player has reached his/her destination
            playerCoordinates.set(playerDestinationCoordinates);
        }
    }

}
