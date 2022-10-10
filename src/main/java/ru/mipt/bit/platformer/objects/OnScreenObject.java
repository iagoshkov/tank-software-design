package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;

public class OnScreenObject {
    protected ObjectGraphics objectGraphics;
    public ObjectGraphics getObjectGraphics() {
        return objectGraphics;
    }
    protected float rotation = 0f;
    public float getRotation() {
        return rotation;
    }
    protected GridPoint2 coordinates;
    public OnScreenObject (GridPoint2 coordinates) {
        this.coordinates = new GridPoint2(coordinates);
    }
    public OnScreenObject (String path, GridPoint2 coordinates) {
        this.objectGraphics = new ObjectGraphics(path);
        this.coordinates = new GridPoint2(coordinates);
    }

    public GridPoint2 getCoordinates() {
        return this.coordinates;
    }

    public void draw(Batch batch) {
        objectGraphics.draw(batch, this.rotation);
    }
}
