package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Disposable;

import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;

public class LevelRenderer implements Disposable {
    private final Batch batch;
    private final TiledMap map;
    private final MapRenderer mapRenderer;
    private final List<TankGraphics> tankGraphics;
    private final ObstacleGraphics obstacleGraphics;

    public LevelRenderer(TiledMap map, List<TankGraphics> tankGraphics, ObstacleGraphics obstacleGraphics) {
        this.batch = new SpriteBatch();
        this.map = map;
        this.mapRenderer = createSingleLayerMapRenderer(map, batch);
        this.tankGraphics = tankGraphics;
        this.obstacleGraphics = obstacleGraphics;
    }

    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // move tank rectangles between tiles
        for (var tank : tankGraphics) {
            tank.move();
        }

        // render each tile
        mapRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render tanks
        for (var tank : tankGraphics) {
            tank.draw(batch);
        }
        // render obstacles
        obstacleGraphics.draw(batch);

        // submit all drawing commands
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
        for (var tank : tankGraphics) {
            tank.dispose();
        }
        obstacleGraphics.dispose();
    }
}
