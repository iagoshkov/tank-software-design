package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject {
    protected GridPoint2 coordinates;
    protected Rectangle bounds;
    protected float rotation;

    public GameObject(GridPoint2 coordinates) {
        this.coordinates = new GridPoint2(coordinates);
        this.rotation = 0f;
    }

    public abstract void draw(Batch batch);
    public abstract void dispose();

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}