package ru.mipt.bit.platformer.movement;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class TileBasedMovement implements MovementStrategy {
    private final GameObject gameObject;
    private final TileMovement tileMovement;
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private float speed;
    private GridPoint2 currentDirection;
    
    public TileBasedMovement(GameObject gameObject, TileMovement tileMovement, float speed) {
        this.gameObject = gameObject;
        this.tileMovement = tileMovement;
        this.speed = speed;
        this.destinationCoordinates = new GridPoint2(gameObject.getCoordinates());
    }
    
    @Override
    public boolean canMoveTo(GridPoint2 target) {
        return movementProgress >= 1f;
    }
    
    @Override
    public void moveTo(GridPoint2 target) {
        if (canMoveTo(target)) {
            this.destinationCoordinates = new GridPoint2(target);
            this.movementProgress = 0f;
            this.currentDirection = new GridPoint2(
                target.x - gameObject.getCoordinates().x,
                target.y - gameObject.getCoordinates().y
            );
        }
    }
    
    @Override
    public void update(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, speed);
        tileMovement.moveRectangleBetweenTileCenters(
            gameObject.getRectangle(), 
            gameObject.getCoordinates(), 
            destinationCoordinates, 
            movementProgress
        );
        
        if (movementProgress >= 1f) {
            gameObject.getCoordinates().set(destinationCoordinates);
        }
    }
    
    @Override
    public boolean isMoving() {
        return movementProgress < 1f;
    }
    
    @Override
    public GridPoint2 getCurrentDirection() {
        return currentDirection != null ? new GridPoint2(currentDirection) : new GridPoint2(0, 0);
    }
    
       public void setSpeed(float speed) {
        this.speed = speed;
    }
}