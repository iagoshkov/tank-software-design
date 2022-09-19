package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Player extends OnScreenObject {
    private float rotation = 0f;
    private float movementProgress = 1f;
    private GridPoint2 destinationCoordinates;

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

    private boolean willHitObstacle(int x, int y, OnScreenObject obstacle) {
        return (x == 1 && obstacle.getCoordinates().equals(incrementedX(coordinates))) ||
                (x == -1 && obstacle.getCoordinates().equals(decrementedX(coordinates))) ||
                (y == 1 && obstacle.getCoordinates().equals(incrementedY(coordinates))) ||
                (y == -1 && obstacle.getCoordinates().equals(decrementedY(coordinates)));
    }

    public Player (String path, int[] coordinates) {
        super(path, coordinates);
        this.destinationCoordinates = new GridPoint2(coordinates[0], coordinates[1]);
    }

    public void setMovementProgress(float progress) {
        this.movementProgress = progress;
    }

    public void movePlayer (int x, int y, OnScreenObject obstacle) {
        if (isEqual(this.movementProgress, 1f)) {
            // check potential player destination for collision with obstacles
            if (!willHitObstacle(x, y, obstacle)) {
                this.destinationCoordinates.add(x, y);
                this.movementProgress = 0f;
            }
            setRotation(x, y);
        }
    }

    public void movePlayerToDestination () {
        if (isEqual(this.movementProgress, 1f)) {
            // record that the player has reached his/her destination
            this.coordinates.set(destinationCoordinates);
        }
    }

    public void movePlayerBetweenTileCenters(TileMovement tileMovement) {
        tileMovement.moveRectangleBetweenTileCenters(this.rectangle, this.coordinates, this.destinationCoordinates, this.movementProgress);
    }

    public float GetRotation() {
        return this.rotation;
    }

    public float getMovementProgress() {
        return this.movementProgress;
    }
}
