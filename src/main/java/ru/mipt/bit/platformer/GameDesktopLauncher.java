package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.List;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {
    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;

    private List<Obstacle> obstacles;
    private Player player;

    private static final int[] DIRECTION_KEYS = new int[]{RIGHT, UP, LEFT, DOWN, W, A, S, D};

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);

        obstacles = List.of(new Obstacle(
                new Texture("images/greenTree.png"),
                new GridPoint2(1, 3), 0f
        ));

        for (var obstacle : obstacles) {
            moveObjectAtTileCenter(groundLayer, obstacle);
        }

        player = new Player(
                new Texture("images/tank_blue.png"),
                new GridPoint2(1, 1), 0,
                new TileMovement(groundLayer, Interpolation.smooth)
        );
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        for (var key : DIRECTION_KEYS) {
            if (Gdx.input.isKeyPressed(key)) {
                player.tryRotateAndStartMovement(Direction.fromKey(key), obstacles);
            }
        }

        player.move();
        player.makeProgress(deltaTime);
        player.tryFinishMovement();

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        drawObjectUnscaled(batch, player);

        // render obstacles
        for (var obstacle : obstacles) {
            drawObjectUnscaled(batch, obstacle);
        }

        // submit all drawing requests
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        // dispose of all the native resources
        for (var obstacle : obstacles) {
            obstacle.dispose();
        }
        player.dispose();
        level.dispose();
        batch.dispose();
    }
}
