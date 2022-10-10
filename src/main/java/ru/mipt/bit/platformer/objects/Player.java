package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.Objects;

import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Player extends OnScreenObject {
    private final GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;

    public float getMovementProgress() {
        return movementProgress;
    }
    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }
    private void setRotation(GridPoint2 movement) {
        if (movement.x > 0) {
            this.rotation = 0;
        } else if (movement.x < 0) {
            this.rotation = -180f;
        } else if (movement.y > 0) {
            this.rotation = 90f;
        } else if (movement.y < 0) {
            this.rotation = -90f;
        }
    }

    private boolean collides (ArrayList<OnScreenObject> allCoordinates, GridPoint2 newCoordinates) {
        for (var coord : allCoordinates) {
            if (Objects.equals(coord.coordinates, newCoordinates)) {
                return true;
            }
        }
        return false;
    }

    public Player (String path, GridPoint2 coordinates) {
        super(path, coordinates);
        this.destinationCoordinates = new GridPoint2(coordinates);
    }

    public Player (GridPoint2 coordinates) {
        super(coordinates);
        this.destinationCoordinates = new GridPoint2(coordinates);
    }

    public void update(GridPoint2 movement, ArrayList<OnScreenObject> obstacles,
                       float deltaTime, float movementSpeed) {
        this.movementProgress = continueProgress(this.movementProgress, deltaTime, movementSpeed);

        if (movementProgress == 1f) {
            this.coordinates.set(destinationCoordinates);
            GridPoint2 coordinatesAfterMove = new GridPoint2(this.coordinates).add(movement);
            if (!collides(obstacles, coordinatesAfterMove)) {
                this.destinationCoordinates.set(coordinatesAfterMove);
                this.movementProgress = 0f;
            }
            this.setRotation(movement);
        }
    }
}
