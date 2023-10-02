package ru.mipt.bit.platformer.entities;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.instructions.Instruction;

import java.util.List;

public interface MapObject {
    GridPoint2 getCoordinates();
    float getRotation();
    default void apply(Instruction key, List<MapObject> objects) {}
}
