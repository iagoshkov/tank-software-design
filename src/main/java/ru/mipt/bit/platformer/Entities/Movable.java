package ru.mipt.bit.platformer.Entities;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Instructions.Direction;

import java.util.List;

public interface Movable extends MapObject {
    GridPoint2 getDestinationCoordinates();
    float getMovementProgress();
    void moveIfNotCollides(Direction direction, List<MapObject> obstacles);
    boolean collides(GridPoint2 targetCoordinates, List<MapObject> others);
}
