package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Control;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Player {
//    public TextureRegion graphics;
//    public Rectangle rectangle;
    private PlayerTexture texture;
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private GridPoint2 coordinates;
    // which tile the player want to go next
    private GridPoint2 destinationCoordinates;
    private float movementProgress;
    private float rotation;

//    public GridPoint2 directionVector = new GridPoint2(0, 0);
//    public float movementRotation;

    public Movement nextMove;

    public Player(Texture tankTexture, GridPoint2 destinationCoordinates){
        texture = new PlayerTexture(tankTexture);
        this.destinationCoordinates = destinationCoordinates;
        coordinates = new GridPoint2(destinationCoordinates);
        rotation = 0f;
        movementProgress = 1f;
        nextMove = new Movement();
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

    public boolean notObstacleAhead(GridPoint2 obstacleCoordinates) {
        GridPoint2 possibleCoordinates = tryMovement();
        return !obstacleCoordinates.equals(possibleCoordinates);
    }

    public void move(Input input, GridPoint2 treeObstacleCoordinates, float movementSpeed) {
        nextMove = Control.determineDirectionByKey(Gdx.input);
        if (!nextMove.isNull() && hasFinishedMovement()){
            makeRotation();
            // if there is no tree ahead
            if (notObstacleAhead(treeObstacleCoordinates)){
                makeMovement();
                finishMovement();
            }
        }

        float deltaTime = Gdx.graphics.getDeltaTime();
        updateMovementProgress(deltaTime, movementSpeed);
        if (hasFinishedMovement()) {
            // record that the player has reached his/her destination
            coordinates.set(destinationCoordinates);
        }
    }

    public void updateMovementProgress(float deltaTime, float movementSpeed) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
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

    public PlayerTexture getTexture() {
        return texture;
    }

    public void dispose(){
        texture.getBlueTank().dispose();
    }
}

