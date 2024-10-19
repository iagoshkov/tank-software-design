package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Objects;

public abstract class GameObjectAbt implements GameObject {
    protected final GridPoint2 coordinates;
    protected float rotation;

    public GameObjectAbt(GridPoint2 coordinates, float rotation) {
        this.coordinates = coordinates;
        this.rotation = rotation;
    }

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    @Override
    public void setCoordinates(GridPoint2 coordinates) {
        this.coordinates.set(coordinates);
    }

    @Override
    public float getRotation() {
        return rotation;
    }

    @Override
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameObject)) return false;
        GameObject gobject = (GameObject) o;
        return Objects.equals(getCoordinates(), gobject.getCoordinates());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCoordinates());
    }
}
