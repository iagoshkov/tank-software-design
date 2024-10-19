package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.keys.Direction;
import ru.mipt.bit.platformer.levels.Level;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.Collection;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;
import static ru.mipt.bit.platformer.util.GdxGameUtils.incrementedX;

public class Ghost extends GameObjectAbt implements Movable {

    protected final float movementSpeed;
    protected final GridPoint2 destinationCoordinates;
    protected float movementProgress;

    public Ghost(GridPoint2 coordinates, float movementSpeed, float movementProgress) {
        super(coordinates, 0f);
        this.movementProgress = movementProgress;
        this.movementSpeed = movementSpeed;
        destinationCoordinates = new GridPoint2(coordinates);
    }

    @Override
    public float getMovementSpeed() {
        return movementSpeed;
    }

    @Override
    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    @Override
    public float getMovementProgress() {
        return movementProgress;
    }

    @Override
    public void setMovementProgress(float movementProgress) {
        this.movementProgress = movementProgress;
    }

    @Override
    public void changeDestinationCoordinates(GridPoint2 direction) {
        destinationCoordinates.x += direction.x;
        destinationCoordinates.y += direction.y;
    }

    @Override
    public void changeMovementState(float deltaTime) {
        setMovementProgress(continueProgress(movementProgress, deltaTime, movementSpeed));
        if (isEqual(movementProgress, 1f)) {
            setCoordinates(destinationCoordinates);
        }
    }

    @Override
    public void move(Direction direction) {
        changeDestinationCoordinates(direction.getDirection());
        setMovementProgress(0f);
    }

    private void canRotateToDirection(Direction direction) {
        if (isEqual(movementProgress, 1f)) {
            setRotation(direction.getRotation());
        }
    }

    @Override
    public boolean canMoveToDirection(Direction direction, Collection<? extends GameObject> obstacles, Level level) {
        canRotateToDirection(direction);
        return isEqual(movementProgress, 1f) && !existCollisions(direction, obstacles) && !outOfBorders(direction, level);
    }

    private boolean outOfBorders(Direction direction, Level level) {
        GridPoint2 destCoordinates = new GridPoint2(coordinates).add(direction.getDirection());
        return (destCoordinates.x > level.getWidth() - 1 || destCoordinates.y > level.getHeight() - 1) ||
               (destCoordinates.x < 0                    || destCoordinates.y < 0);
    }

    private boolean existCollisions(Direction direction, Collection<? extends GameObject> obstacles) {
        return obstacles.stream().anyMatch(obstacle -> obstacle.getCoordinates().equals(new GridPoint2(coordinates).add(direction.getDirection())));
    }

}
