package ru.mipt.bit.platformer.objects.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.driver.CollisionChecker;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.objects.Movement;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Bullet implements GameObject {
    public final int damage = 33;
    private final GridPoint2 coordinates;
    private final GridPoint2 destinationCoordinates;
    private final float rotation;
    private final Movement direction;
    private boolean existent = true;
    private float movementProgress = 1f;

    private final Tank tank;

    private final CollisionChecker collisionChecker;

    public Bullet(GridPoint2 coords, float rotation, CollisionChecker collisionChecker, Tank tank) {
        this.coordinates = new GridPoint2(coords);
        this.destinationCoordinates = new GridPoint2(coords);
        this.rotation = rotation;
        this.direction = getDirectionFromRotation();

        this.collisionChecker = collisionChecker;

        this.tank = tank;
    }

    private Movement getDirectionFromRotation() {
        if (rotation == Direction.UP.rotation) {
            return new Movement(Direction.UP.vector, Direction.UP.rotation);
        }
        if (rotation == Direction.RIGHT.rotation) {
            return new Movement(Direction.RIGHT.vector, Direction.RIGHT.rotation);
        }
        if (rotation == Direction.DOWN.rotation) {
            return new Movement(Direction.DOWN.vector, Direction.DOWN.rotation);
        }

        return new Movement(Direction.LEFT.vector, Direction.LEFT.rotation);
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public Tank getTank() {
        return tank;
    }

    public float getRotation() {
        return rotation;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isExistent() {
        return existent;
    }

    public void setNotExistent() {
        existent = false;
    }

    public boolean hasFinishedMovement() {
        return isEqual(movementProgress, 1f);
    }

    public GridPoint2 tryMovement() {
        GridPoint2 newCoordinates = new GridPoint2();
        newCoordinates.x = destinationCoordinates.x + direction.directionVector.x;
        newCoordinates.y = destinationCoordinates.y + direction.directionVector.y;
        return newCoordinates;
    }

    public boolean noCollisions() {
        GridPoint2 newCoordinates = tryMovement();
        return collisionChecker.noCollisionsForBullet(newCoordinates, this) &&
                collisionChecker.noCollisionsForBullet(coordinates, this);
    }

    public boolean isMovementPossible(GridPoint2 obstacleCoordinates, GridPoint2 newPosition) {
        return !obstacleCoordinates.equals(newPosition);
    }

    public void makeMovement() {
        destinationCoordinates.add(direction.directionVector);
    }

    public void finishMovement() {
        movementProgress = 0f;
    }

    public void updateMovementProgress(float deltaTime, float movementSpeed) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
    }

    public void move() {
        if (hasFinishedMovement()) {
            if (noCollisions()){
                makeMovement();
                finishMovement();
            }
        }

        float deltaTime = Gdx.graphics.getDeltaTime();
        float MOVEMENT_SPEED = 0.3f;
        updateMovementProgress(deltaTime, MOVEMENT_SPEED);
        if (hasFinishedMovement()) {
            coordinates.set(destinationCoordinates);
        }
    }




}
