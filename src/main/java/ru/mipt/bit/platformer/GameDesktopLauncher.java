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
import ru.mipt.bit.platformer.util.GdxKeyboardListener;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class GameDesktopLauncher implements ApplicationListener {
    private Batch batch;


    private TiledMap level;
    private MapRenderer levelRenderer;


    private Tank player;
    private Texture blueTankTexture;
    private Texture greenTreeTexture;
    private ArrayList<Obstacle> obstacles;
    private Obstacle tree;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private GdxKeyboardListener keyboardListener;

    @Override
    public void render() {
        clearScreen();

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();
        player.move(deltaTime, gameObjects, keyboardListener);

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();
        for (GameObject gameObject : gameObjects) {
            gameObject.draw(batch);
        }
        // submit all drawing requests
        batch.end();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        // load game objects
        blueTankTexture = new Texture("images/tank_blue.png");
        player = new Tank(groundLayer, blueTankTexture, new GridPoint2(1, 1), 0f, tileMovement);
        gameObjects.add(player);
        greenTreeTexture = new Texture("images/greenTree.png");
        tree = new Obstacle(groundLayer, greenTreeTexture, new GridPoint2(1, 3), 0f);
        gameObjects.add(tree);

//        // initialize level
//        Level level = new Level(new LevelFromFileGenerator("src/main/resources/disposition.txt"));
//        level.initObjects();
//        player = level.getPlayer();
//        obstacles = level.getObstacles();
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
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        blueTankTexture.dispose();
        greenTreeTexture.dispose();
        level.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}

