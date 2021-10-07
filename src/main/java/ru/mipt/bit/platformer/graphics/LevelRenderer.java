package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Disposable;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;

public class LevelRenderer implements Disposable {
    private final Batch batch;
    private final TiledMap map;
    private final MapRenderer mapRenderer;
    private final PlayerGraphics playerGraphics;
    private final ObstacleGraphics obstacleGraphics;

    public LevelRenderer(TiledMap map, PlayerGraphics playerGraphics, ObstacleGraphics obstacleGraphics) {
        this.batch = new SpriteBatch();
        this.map = map;
        this.mapRenderer = createSingleLayerMapRenderer(map, batch);
        this.playerGraphics = playerGraphics;
        this.obstacleGraphics = obstacleGraphics;
    }

    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // move player rectangle between tiles
        playerGraphics.move();

        // render each tile
        mapRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        playerGraphics.draw(batch);

        // render obstacles
        obstacleGraphics.draw(batch);

        // submit all drawing commands
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
        playerGraphics.dispose();
        obstacleGraphics.dispose();
    }
}
