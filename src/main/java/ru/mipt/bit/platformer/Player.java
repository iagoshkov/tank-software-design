package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.List;

import static com.badlogic.gdx.math.MathUtils.clamp;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class Player implements Renderable, Disposable {
    private static final float MOVEMENT_SPEED = 0.4f;

    private final TileMovement tileMovement;
    private final TextureRegion region;
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private final GridPoint2 coordinates;
    // which tile the player want to go next
    private GridPoint2 destinationCoordinates;
    private final Rectangle rectangle;
    private float rotation;
    private float movementProgress = 1f;

    public Player(Texture texture, GridPoint2 coordinates, float rotation, TileMovement tileMovement) {
        this.region = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(region);
        // set player initial position
        this.coordinates = coordinates;
        this.destinationCoordinates = coordinates;
        this.rotation = rotation;
        this.tileMovement = tileMovement;
    }

    @Override
    public TextureRegion getRegion() {
        return region;
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public float getRotation() {
        return rotation;
    }

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public boolean isMoving() {
        return movementProgress < 1f;
    }

    public boolean hasFinishedMovement() {
        return movementProgress == 1f;
    }

    public void tryFinishMovement() {
        if (hasFinishedMovement()) {
            coordinates.set(destinationCoordinates);
        }
    }

    public void tryRotateAndStartMovement(Direction direction, List<Obstacle> obstacles) {
        if (isMoving()) {
            return;
        }
        rotation = direction.calcAngle();
        var newDestination = direction.calcDestinationCoordinatesFrom(coordinates);
        boolean canMove = true;
        for (var obstacle : obstacles) {
            if (obstacle.getCoordinates().equals(newDestination)) {
                canMove = false;
                break;
            }
        }
        if (canMove) {
            destinationCoordinates = newDestination;
            movementProgress = 0f;
        }
    }

    public void move() {
        tileMovement.moveRectangleBetweenTileCenters(rectangle, coordinates, destinationCoordinates, movementProgress);
    }

    public void makeProgress(float deltaTime) {
        movementProgress = clamp(movementProgress + deltaTime / MOVEMENT_SPEED, 0f, 1f);
    }

    public void dispose() {
        region.getTexture().dispose();
    }
}
