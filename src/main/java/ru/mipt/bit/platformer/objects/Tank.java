package ru.mipt.bit.platformer.objects;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.movement.Colliding;
import ru.mipt.bit.platformer.movement.CollisionChecker;
import ru.mipt.bit.platformer.movement.Direction;

import java.util.List;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class Tank implements Colliding {
    private GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private static final float MOVEMENT_SPEED = 0.4f;
    public static final float MOVEMENT_COMPLETED = 1f;
    public static final int MOVEMENT_STARTED = 0;
    private float rotation;
    private float movementProgress = MOVEMENT_COMPLETED;
    private final CollisionChecker collisionChecker;

    public float getSpeed() {
        return MOVEMENT_SPEED;
    }
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public Tank(GridPoint2 initialCoordinates, CollisionChecker collisionChecker) {
        destinationCoordinates = initialCoordinates;
        coordinates = new GridPoint2(destinationCoordinates);
        rotation = 0f;
        this.collisionChecker = collisionChecker;
    }

    private boolean canMove(Direction direction, GridPoint2 obstacleCoordinates) {
        GridPoint2 newCoordinates = direction.apply(this.coordinates);
        return !newCoordinates.equals(obstacleCoordinates);
    }


    public void tryMove(List<Obstacle> obstacles, Direction direction) {
        if (notMoving() && Direction.NODIRECTION != direction) {
//            boolean canMove = true;
//            for (Obstacle obstacle : obstacles) {
//                GridPoint2 obstacleCoordinates = obstacle.getCoordinates();
//                if (!canMove(direction, obstacleCoordinates)) {
//                    canMove = false;
//                    break;
//                }
            GridPoint2 newCoordinates = direction.apply(coordinates);
            if (collisionChecker.isFree(newCoordinates)) {
                destinationCoordinates = newCoordinates;
                startMovement();
                rotation = direction.getAngle();
            }
//            if (canMove) {
//                destinationCoordinates = direction.apply(destinationCoordinates);
//                startMovement();
//                rotation = direction.getAngle();
//            }
        }
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public float getRotation() {
        return rotation;
    }

    private boolean notMoving() {
        return isEqual(movementProgress, MOVEMENT_COMPLETED);
    }

    public void tryReachDestinationCoordinates(float newMovementProgress) {
        movementProgress = newMovementProgress;
        if (isEqual(newMovementProgress, MOVEMENT_COMPLETED)) {
            // record that the player has reached his/her destination
            coordinates = destinationCoordinates;
        }
    }

    @Override
    public boolean collides(GridPoint2 target) {
        return coordinates.equals(target);
    }

    private void startMovement() {
        movementProgress = MOVEMENT_STARTED;
    }
}
