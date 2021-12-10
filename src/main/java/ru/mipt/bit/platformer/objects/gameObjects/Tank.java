package ru.mipt.bit.platformer.objects.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.driver.CollisionChecker;
import ru.mipt.bit.platformer.objects.*;
import ru.mipt.bit.platformer.objects.states.*;

import java.util.Date;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank implements GameObject {

    private float MOVEMENT_SPEED = 0.4f;

    private final GridPoint2 coordinates;
    private final GridPoint2 destinationCoordinates;
    private float movementProgress;
    private float rotation;
    private Movement nextMove;

    private boolean alive;
    private int life = 99;
    private State state;

    private long lastTimeShooting = new Date().getTime();

    private final CollisionChecker collisionChecker;

    public Tank(GridPoint2 destinationCoordinates, CollisionChecker collisionChecker) {
        this.destinationCoordinates = destinationCoordinates;
        coordinates = new GridPoint2(destinationCoordinates);
        rotation = 0f;
        movementProgress = 1f;
        nextMove = new Movement();
        alive = true;
        this.collisionChecker = collisionChecker;
        state = new NoDamageState(this);
    }

    public long getLastTimeShooting() {
        return lastTimeShooting;
    }

    public float getMovementSpeed() {
        return MOVEMENT_SPEED;
    }

    public int getLife() {
        return life;
    }

    public float getRotation() {
        return rotation;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }

    public void setLastTimeShooting(long time) {
        lastTimeShooting = time;
    }

    public void setMovementSpeed(float MOVEMENT_SPEED) {
        this.MOVEMENT_SPEED = MOVEMENT_SPEED;
    }

    public boolean hasFinishedMovement() {
        return isEqual(movementProgress, 1f);
    }

    public void makeRotation() {
        rotation = nextMove.rotation;
    }

    public void makeMovement() {
        destinationCoordinates.add(nextMove.directionVector);
    }

    public GridPoint2 tryMovement() {
        GridPoint2 newCoordinates = new GridPoint2();
        newCoordinates.x = destinationCoordinates.x + nextMove.directionVector.x;
        newCoordinates.y = destinationCoordinates.y + nextMove.directionVector.y;
        return newCoordinates;
    }

    public void finishMovement() {
        nextMove.directionVector.x = 0;
        nextMove.directionVector.y = 0;
        movementProgress = 0f;
    }

    public boolean isMovementPossible(GridPoint2 obstacleCoordinates, GridPoint2 newPosition) {
        return !obstacleCoordinates.equals(newPosition);
    }

    public void moveCommand() {
        if (!nextMove.isNull() && hasFinishedMovement()) {
            makeRotation();
            if (noCollisions()){
                makeMovement();
                finishMovement();
            }
        }

        float deltaTime = Gdx.graphics.getDeltaTime();
        updateMovementProgress(deltaTime, MOVEMENT_SPEED);
        if (hasFinishedMovement()) {
            coordinates.set(destinationCoordinates);
        }
    }

    public void updateMovementProgress(float deltaTime, float movementSpeed) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
    }

    public void moveUp(){
        nextMove = new Movement(new GridPoint2(Direction.UP.vector), Direction.UP.rotation);
    }

    public void moveDown(){
        nextMove = new Movement(new GridPoint2(Direction.DOWN.vector), Direction.DOWN.rotation);
    }

    public void moveLeft(){
        nextMove = new Movement(new GridPoint2(Direction.LEFT.vector), Direction.LEFT.rotation);
    }

    public void moveRight(){
        nextMove = new Movement(new GridPoint2(Direction.RIGHT.vector), Direction.RIGHT.rotation);
    }

    public void shoot(){

    }

    public boolean noCollisions() {
        GridPoint2 newCoordinates = tryMovement();
        return collisionChecker.noCollisionsForTank(newCoordinates, this);
    }

    public boolean canShoot() {
        return state.canShoot();
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void takeDamage(Bullet bullet) {
        life -= bullet.getDamage();
        if (life == 66) {
            state = new MediumDamageState(this);
        } else if (life == 33) {
            state = new SevereDamageState(this);
        }
        if (life <= 0)
            alive = false;
    }
}

