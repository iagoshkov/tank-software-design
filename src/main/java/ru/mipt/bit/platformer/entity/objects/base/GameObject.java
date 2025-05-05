package ru.mipt.bit.platformer.entity.objects.base;

import com.badlogic.gdx.math.GridPoint2;

public interface GameObject {
    void updateState(float deltaTime);
    GridPoint2 getCoordinates();
    boolean isBusyCoordinate(GridPoint2 point);
}
