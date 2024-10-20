package ru.mipt.bit.platformer.levels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class EmptyDrawableLevel implements DrawableLevel {
    protected TiledMap level;
    protected MapRenderer levelRenderer;
    protected TiledMapTileLayer groundLayer;
    protected int width, height;

    public EmptyDrawableLevel(TiledMap level, Batch batch) {
        if (level == null || batch == null) {
            return;
        }
        this.level = level;
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        groundLayer = getSingleLayer(level);
        width = groundLayer.getWidth();
        height = groundLayer.getHeight();
    }

    public MapRenderer getLevelRenderer() {
        return levelRenderer;
    }

    public TiledMap getLevel() {
        return level;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public TiledMapTileLayer getGroundLayer() {
        return groundLayer;
    }

    @Override
    public void dispose() {
        level.dispose();
    }

    @Override
    public void render() {
        levelRenderer.render();
    }
}
