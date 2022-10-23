package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.Objects;

import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Player extends OnScreenObject {
    private GridPoint2 battlefieldDimensions;
    private final GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;

    public boolean isManuallyControlled() {
        return manuallyControlled;
    }

    public void setManuallyControlled(boolean manuallyControlled) {
        this.manuallyControlled = manuallyControlled;
    }

    private boolean manuallyControlled = false;

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

    private boolean collides (ArrayList<? extends OnScreenObject> allCoordinates, GridPoint2 newCoordinates) {
        for (var coord : allCoordinates) {
            if (Objects.equals(coord.coordinates, newCoordinates) || (!this.equals(coord) && coord instanceof Player && Objects.equals(((Player) coord).destinationCoordinates, newCoordinates))) {
                return true;
            }
        }
        return false;
    }

    private boolean outOfBattlefield (GridPoint2 newCoordinates) {
        return newCoordinates.x < 0 || newCoordinates.y < 0 || newCoordinates.x >= battlefieldDimensions.x ||
                newCoordinates.y >= battlefieldDimensions.y;
    }

    public Player (String path, GridPoint2 coordinates, GridPoint2 battlefieldDimensions) {
        super(path, coordinates);
        this.destinationCoordinates = new GridPoint2(coordinates);
        this.battlefieldDimensions = battlefieldDimensions;
    }

    public Player (GridPoint2 coordinates, GridPoint2 battlefieldDimensions) {
        super(coordinates);
        this.destinationCoordinates = new GridPoint2(coordinates);
        this.battlefieldDimensions = battlefieldDimensions;
    }

    public void update(GridPoint2 movement, ArrayList<OnScreenObject> obstacles, ArrayList<Player> players,
                       float deltaTime, float movementSpeed) {
        this.movementProgress = continueProgress(this.movementProgress, deltaTime, movementSpeed);

        if (movementProgress == 1f) {
            this.coordinates.set(destinationCoordinates);
            GridPoint2 coordinatesAfterMove = new GridPoint2(this.coordinates).add(movement);
            if (!collides(obstacles, coordinatesAfterMove) && !collides(players, coordinatesAfterMove) && !outOfBattlefield(coordinatesAfterMove)) {
                this.destinationCoordinates.set(coordinatesAfterMove);
                this.movementProgress = 0f;
            }
            this.setRotation(movement);
        }
    }
}
