package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Player {
    private TextureRegion graphics;
    private Rectangle rectangle;
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private GridPoint2 coordinates;
    // which tile the player want to go next
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private float rotation;

    public Rectangle getRectangle() {
        return rectangle;
    }

    public TextureRegion getGraphics() {
        return graphics;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public float getRotation() {
        return rotation;
    }

    public Player(Texture blueTankTexture) {
        graphics = new TextureRegion(blueTankTexture);
        rectangle = createBoundingRectangle(graphics);
        // set player initial position
        destinationCoordinates = new GridPoint2(1, 1);
        coordinates = new GridPoint2(destinationCoordinates);
        rotation = 0f;
    }

    public void TryGoUp(GridPoint2 treeObstacleCoordinates) {
        if (isEqual(movementProgress, 1f)) {
            // check potential player destination for collision with obstacles
            if (!treeObstacleCoordinates.equals(incrementedY(coordinates))) {
                destinationCoordinates.y++;
                movementProgress = 0f;
            }
            rotation = 90f;
        }
    }


    public void TryGoLeft(GridPoint2 treeObstacleCoordinates) {
        if (isEqual(movementProgress, 1f)) {
            if (!treeObstacleCoordinates.equals(decrementedX(coordinates))) {
                destinationCoordinates.x--;
                movementProgress = 0f;
            }
            rotation = -180f;
        }
    }

    public void TryGoDown(GridPoint2 treeObstacleCoordinates) {
        if (isEqual(movementProgress, 1f)) {
            if (!treeObstacleCoordinates.equals(decrementedY(coordinates))) {
                destinationCoordinates.y--;
                movementProgress = 0f;
            }
            rotation = -90f;
        }
    }

    public void TryGoRight(GridPoint2 treeObstacleCoordinates) {
        if (isEqual(movementProgress, 1f)) {
            if (!treeObstacleCoordinates.equals(incrementedX(coordinates))) {
                destinationCoordinates.x++;
                movementProgress = 0f;
            }
            rotation = 0f;
        }
    }

    public void continuePlayerProgress(float deltaTime, float MOVEMENT_SPEED) {
        movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);
        if (isEqual(movementProgress, 1f)) {
            // record that the player has reached his/her destination
            coordinates = destinationCoordinates;
        }
    }

}
