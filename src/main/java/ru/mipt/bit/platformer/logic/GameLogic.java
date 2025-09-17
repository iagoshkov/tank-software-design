package ru.mipt.bit.platformer.logic;

import ru.mipt.bit.platformer.collision.CollisionDetector;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.World;
import com.badlogic.gdx.math.GridPoint2;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameLogic {
    private static final float MOVEMENT_SPEED = 0.4f;

    private final CollisionDetector collisionDetector;

    public GameLogic(CollisionDetector collisionDetector) {
        this.collisionDetector = collisionDetector;
    }

    public void processMoveCommand(World world, Direction direction) {
        if (!world.getPlayer().isMoving()) {
            GridPoint2 currentPos = world.getPlayer().getCoordinates();

            if (collisionDetector.canMove(world, currentPos, direction)) {
                GridPoint2 target = getTargetPosition(currentPos, direction);
                world.getPlayer().setDestination(target);
                world.getPlayer().setRotation(getRotationForDirection(direction));
            }
        }
    }

    public void updateWorld(World world, float deltaTime) {
        world.getPlayer().updateProgress(deltaTime, MOVEMENT_SPEED);
    }

    private GridPoint2 getTargetPosition(GridPoint2 from, Direction direction) {
        return switch (direction) {
            case UP -> incrementedY(from);
            case LEFT -> decrementedX(from);
            case DOWN -> decrementedY(from);
            case RIGHT -> incrementedX(from);
        };
    }

    private float getRotationForDirection(Direction direction) {
        return switch (direction) {
            case UP -> 90f;
            case LEFT -> -180f;
            case DOWN -> -90f;
            case RIGHT -> 0f;
        };
    }
}