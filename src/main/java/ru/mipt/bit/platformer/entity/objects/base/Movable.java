package ru.mipt.bit.platformer.entity.objects.base;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.playerinput.inputs.Direction;

public interface Movable {
    float MOVEMENT_PROGRESS_MOVE = 1f;
    float MOVEMENT_PROGRESS_TURN = 0f;
    float movementSpeed = 0.8f;

    void move(Direction direction, boolean hasWay);
    GridPoint2 getDestinationCoordinates();
    GridPoint2 getCoordinates();
    Direction getDirection();
    float getMovementProgress();
}
