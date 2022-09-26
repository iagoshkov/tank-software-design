package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Player extends OnScreenObject {
    private float movementProgress = 1f;
    private final GridPoint2 destinationCoordinates;
    private final int[] coordinatesAdd = {0, 0};

    private void setRotation(int x, int y) {
        if (x > 0) {
            this.rotation = 0;
        } else if (x < 0) {
            this.rotation = -180f;
        } else if (y > 0) {
            this.rotation = 90f;
        } else if (y < 0) {
            this.rotation = -90f;
        }
    }

    private boolean willHitObstacle(OnScreenObject obstacle) {
        return (coordinatesAdd[0] == 1 && obstacle.getCoordinates().equals(incrementedX(coordinates))) ||
                (coordinatesAdd[0] == -1 && obstacle.getCoordinates().equals(decrementedX(coordinates))) ||
                (coordinatesAdd[1] == 1 && obstacle.getCoordinates().equals(incrementedY(coordinates))) ||
                (coordinatesAdd[1] == -1 && obstacle.getCoordinates().equals(decrementedY(coordinates)));
    }

    public Player (String path, int[] coordinates) {
        super(path, coordinates);
        this.destinationCoordinates = new GridPoint2(coordinates[0], coordinates[1]);
    }

    private void updateCoordinatesIfNoCollision(OnScreenObject obstacle) {
        if (isEqual(this.movementProgress, 1f)) {
            if (!willHitObstacle(obstacle)) {
                this.destinationCoordinates.add(coordinatesAdd[0], coordinatesAdd[1]);
                this.movementProgress = 0f;
            }
            this.setRotation(coordinatesAdd[0], coordinatesAdd[1]);
        }
    }

    private void recordIfReachedDestination() {
        if (isEqual(this.movementProgress, 1f)) {
            this.coordinates.set(destinationCoordinates);
        }
    }

    public void move(Input input, OnScreenObject obstacle, TileMovement tileMovement,
                     float deltaTime, float movementSpeed) {
        this.processUserInput(input, obstacle);

        tileMovement.moveRectangleBetweenTileCenters(this.rectangle, this.coordinates, this.destinationCoordinates, this.movementProgress);
        this.movementProgress = continueProgress(this.movementProgress, deltaTime, movementSpeed);
        this.recordIfReachedDestination();
    }

    private void processUserInput(Input input, OnScreenObject obstacle) {
        coordinatesAdd[0] = 0;
        coordinatesAdd[1] = 0;
        if (input.isKeyPressed(UP) || input.isKeyPressed(W)) {
            coordinatesAdd[1] = 1;
        }
        if (input.isKeyPressed(LEFT) || input.isKeyPressed(A)) {
            coordinatesAdd[0] = -1;
        }
        if (input.isKeyPressed(DOWN) || input.isKeyPressed(S)) {
            coordinatesAdd[1] = -1;
        }
        if (input.isKeyPressed(RIGHT) || input.isKeyPressed(D)) {
            coordinatesAdd[0] = 1;
        }
        this.updateCoordinatesIfNoCollision(obstacle);
    }

}
