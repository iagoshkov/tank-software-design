package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.keys.Direction;
import ru.mipt.bit.platformer.levels.Level;

import java.util.Collection;

public interface Movable extends GameObject {
    float getMovementSpeed();

    GridPoint2 getDestinationCoordinates();

    float getMovementProgress();

    void setMovementProgress(float movementProgress);

    void changeDestinationCoordinates(GridPoint2 direction);

    void changeMovementState(float deltaTime);

    void move(Direction direction);

    boolean canMoveToDirection(Direction direction, Collection<? extends GameObject> obstacles, Level level);
}
