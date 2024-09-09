package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;

import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch;
    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;
    private Tank playerTank;
    private Tree treeObstacle;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // Load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        // Create tank and tree objects
        playerTank = new Tank("images/tank_blue.png", groundLayer, tileMovement, MOVEMENT_SPEED, 1, 1);
        treeObstacle = new Tree("images/greenTree.png", groundLayer, 1, 3);
    }

    @Override
    public void render() {
        // Clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // Handle player input and movement
        playerTank.handleInput(treeObstacle.getCoordinates());

        // Render each tile of the level
        levelRenderer.render();

        // Start recording all drawing commands
        batch.begin();

        // Render objects
        playerTank.render(batch);
        treeObstacle.render(batch);

        // Submit all drawing requests
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Do not react to window resizing
    }

    @Override
    public void pause() {
        // Game doesn't get paused
    }

    @Override
    public void resume() {
        // Game doesn't get paused
    }

    @Override
    public void dispose() {
        // Dispose of all the native resources
        playerTank.dispose();
        treeObstacle.dispose();
        level.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
