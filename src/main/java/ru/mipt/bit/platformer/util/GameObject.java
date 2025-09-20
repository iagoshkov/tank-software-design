package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject {
    protected GridPoint2 coordinates;
    protected Rectangle rectangle;
    protected TextureRegion graphics;
    protected float rotation;

    public GameObject(GridPoint2 coordinates, TextureRegion graphics, TiledMapTileLayer groundLayer) {
        this.coordinates = new GridPoint2(coordinates);
        this.graphics = graphics;
        this.rectangle = GdxGameUtils.createBoundingRectangle(graphics);
        this.rotation = 0f;
        updateRectanglePosition(groundLayer);
    }

    public void updateRectanglePosition(TiledMapTileLayer groundLayer) {
        GdxGameUtils.moveRectangleAtTileCenter(groundLayer, rectangle, coordinates);
    }

    public void render(Batch batch) {
        GdxGameUtils.drawTextureRegionUnscaled(batch, graphics, rectangle, rotation);
    }

    public void setPosition(GridPoint2 newPosition, TiledMapTileLayer groundLayer) {
        this.coordinates.set(newPosition);
        updateRectanglePosition(groundLayer);
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public float getRotation() {
        return rotation;
    }

    public abstract void update(float deltaTime);
}