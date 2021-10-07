package ru.mipt.bit.platformer.movables;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entities.Direction;

public interface Movable {
    void move(float deltaTime);
    void setRotation(float rotation);
    void triggerMovement(Direction direction);
    GridPoint2 getCoordinatesAfterShift(Direction direction);
}
