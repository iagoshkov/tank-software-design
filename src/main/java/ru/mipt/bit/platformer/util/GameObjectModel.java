package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

public abstract class GameObjectModel {
    protected GridPoint2 coordinates;
    protected float rotation;

    public GameObjectModel(GridPoint2 coordinates) {
        this.coordinates = new GridPoint2(coordinates);
        this.rotation = 0f;
    }

    public abstract void update(float deltaTime);

    public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates);
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}