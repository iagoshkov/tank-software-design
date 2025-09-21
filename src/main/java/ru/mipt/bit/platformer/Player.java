package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.List;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Player extends RigidBody {

    private static final float MOVEMENT_SPEED = 0.4f;

    private final TileMovement tileMovement;
    private final GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;

    public Player(Texture texture, TileMovement tileMovement, GridPoint2 startCoordinates) {
        super(texture, startCoordinates);
        this.tileMovement = tileMovement;
        this.destinationCoordinates = new GridPoint2(startCoordinates);
    }

    @Override
    public void update(float deltaTime, List<GridPoint2> obstacleCoordinates) {
        handleInput(obstacleCoordinates);

        tileMovement.moveRectangleBetweenTileCenters(
                getRectangle(), getCoordinates(), destinationCoordinates, movementProgress
        );

        movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);
        if (isEqual(movementProgress, 1f)) {
            getCoordinates().set(destinationCoordinates);
        }
    }

    private void handleInput(List<GridPoint2> obstacles) {
        if (!isMoving()) {
            if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
                tryMove(getCoordinates().x, getCoordinates().y + 1, 90f, obstacles);
            } else if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
                tryMove(getCoordinates().x - 1, getCoordinates().y, 180f, obstacles);
            } else if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
                tryMove(getCoordinates().x, getCoordinates().y - 1, 270f, obstacles);
            } else if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
                tryMove(getCoordinates().x + 1, getCoordinates().y, 0f, obstacles);
            }
        }
    }

    private void tryMove(int newX, int newY, float rotation, List<GridPoint2> obstacles) {
        setRotation(rotation);

        GridPoint2 target = new GridPoint2(newX, newY);
        if (!obstacles.contains(target)) {
            destinationCoordinates.set(newX, newY);
            movementProgress = 0f;
        }
    }

    public boolean isMoving() {
        return !isEqual(movementProgress, 1f);
    }

    private void setRotation(float degrees) {
        super.rotation = degrees;
    }

    @Override
    public void render(Batch batch) {
        super.render(batch);
    }
}
