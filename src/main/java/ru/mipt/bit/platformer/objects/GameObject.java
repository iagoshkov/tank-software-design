package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Objects;

public interface GameObject {
    GridPoint2 getCoordinates();

    void setCoordinates(GridPoint2 coordinates);

    void setRotation(float rotation);

    float getRotation();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
