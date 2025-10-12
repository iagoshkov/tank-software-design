package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.drawers.Renderable;

public abstract class GameObject implements Renderable {
    protected GridPoint2 coordinates;
    protected Rectangle bounds;
    protected float rotation;

    public GameObject(GridPoint2 coordinates) {
        this.coordinates = new GridPoint2(coordinates);
        this.rotation = 0f;
    }

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