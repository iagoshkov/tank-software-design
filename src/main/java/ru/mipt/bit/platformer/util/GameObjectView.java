package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObjectView {
    protected GameObjectModel model;
    protected Rectangle rectangle;
    protected TextureRegion graphics;

    public GameObjectView(GameObjectModel model, TextureRegion graphics, TiledMapTileLayer groundLayer) {
        this.model = model;
        this.graphics = graphics;
        this.rectangle = GdxGameUtils.createBoundingRectangle(graphics);
        updateRectanglePosition(groundLayer);
    }

    public void updateRectanglePosition(TiledMapTileLayer groundLayer) {
        GdxGameUtils.moveRectangleAtTileCenter(groundLayer, rectangle, model.coordinates);
    }

    public void render(Batch batch) {
        GdxGameUtils.drawTextureRegionUnscaled(batch, graphics, rectangle, model.rotation);
    }

    public void setPosition(GridPoint2 newPosition, TiledMapTileLayer groundLayer) {
        model.coordinates.set(newPosition);
        updateRectanglePosition(groundLayer);
    }

    public GameObjectModel getModel() {
        return model;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public abstract void update(float deltaTime);
}