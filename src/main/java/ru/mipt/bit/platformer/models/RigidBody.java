package ru.mipt.bit.platformer.models;

import com.badlogic.gdx.math.GridPoint2;

public abstract class RigidBody {
    protected GridPoint2 coordinates;
    protected float rotation;

    public RigidBody(GridPoint2 startCoordinates) {
        this.coordinates = new GridPoint2(startCoordinates);
        this.rotation = 0f;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public float getRotation() {
        return rotation;
    }

    public abstract void update(float deltaTime);
}
