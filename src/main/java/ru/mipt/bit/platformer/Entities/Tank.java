package ru.mipt.bit.platformer.Entities;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Instructions.Direction;

import java.util.List;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank implements Movable {
    private static final float MOVEMENT_SPEED = 0.4f;
    public static final float MOVEMENT_COMPLETED = 1f;
    public static final int MOVEMENT_STARTED = 0;
    private float movementProgress;
    private GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private Direction direction;

    public Tank(GridPoint2 coordinates, Direction direction) {
        this.movementProgress = 1;
        this.coordinates = coordinates;
        this.destinationCoordinates = coordinates;
        this.direction = direction;
    }

    public boolean isMoving() {
        return !isEqual(movementProgress, MOVEMENT_COMPLETED);
    }

    public void moveTo(GridPoint2 tankTargetCoordinates) {
        destinationCoordinates = tankTargetCoordinates;
        movementProgress = MOVEMENT_STARTED;
    }

    public void rotate(Direction direction) {
        this.direction = direction;
    }

    public void moveIfNotCollides(Direction direction, List<MapObject> obstacles) {
        if (!isMoving()) {
            GridPoint2 targetCoordinates = direction.apply(coordinates);

            if (!collides(targetCoordinates, obstacles)) {
                moveTo(targetCoordinates);
            }

            rotate(direction);
        }
    }

    public void updateState(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);
        if (!isMoving()) {
            coordinates = destinationCoordinates;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean collides(GridPoint2 targetCoordinates, MapObject other) {
        return targetCoordinates.equals(other.getCoordinates());
    }

    public boolean collides(GridPoint2 targetCoordinates, List<MapObject> others) {
        for (MapObject other: others) {
            if (other != this) {
                if (collides(targetCoordinates, other)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    @Override
    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    @Override
    public float getRotation() {
        return getDirection().getRotation();
    }

    @Override
    public float getMovementProgress() {
        return movementProgress;
    }
}