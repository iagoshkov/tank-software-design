package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.objects.OnScreenObject;
import ru.mipt.bit.platformer.objects.Player;
import ru.mipt.bit.platformer.util.TileMovement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {
    private static final float MOVEMENT_SPEED = 0.4f;
    private Batch batch;
    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;
    private Player tank;
    private final ArrayList<OnScreenObject> obstacles = new ArrayList<>();

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        String levelLayout = "level.txt";
        LevelGenerator generator = getLevelGenerator(levelLayout, new int[]{groundLayer.getWidth(), groundLayer.getHeight()});

        tank = new Player("images/tank_blue.png", generator.getPlayerCoordinates());
        for (var coordinatePair : generator.getObstaclesCoordinates()) {
            var o = new OnScreenObject("images/greenTree.png", coordinatePair);
            obstacles.add(o);
            moveRectangleAtTileCenter(groundLayer, o.getObjectGraphics().getRectangle(), o.getCoordinates());
        }
    }

    private static LevelGenerator getLevelGenerator(String levelFilePath, int[] dimensions) {
        LevelGenerator generator;
        File f = new File(levelFilePath);

        if (f.exists() && !f.isDirectory()) {
            try {
                generator = new LevelGenerator(levelFilePath);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            Random rd = new Random();
            generator = new LevelGenerator(dimensions, rd.nextInt(10));
        }
        return generator;
    }

    @Override
    public void render() {
        // clear screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        float deltaTime = Gdx.graphics.getDeltaTime();

        levelRenderer.render();
        batch.begin();

        drawObjects(deltaTime);
        batch.end();
    }

    private void drawObjects(float deltaTime) {
        tileMovement.moveRectangleBetweenTileCenters(tank.getObjectGraphics().getRectangle(), tank.getCoordinates(),
                tank.getDestinationCoordinates(), tank.getMovementProgress());
        tank.update(processUserInput(Gdx.input), obstacles, deltaTime, MOVEMENT_SPEED);
        tank.draw(batch);

        for (var obstacle : obstacles) {
            obstacle.draw(batch);
        }
    }

    private GridPoint2 processUserInput(Input input) {
        GridPoint2 movement = new GridPoint2(0, 0);
        if (input.isKeyPressed(UP) || input.isKeyPressed(W)) {
            movement.y = 1;
        }
        if (input.isKeyPressed(LEFT) || input.isKeyPressed(A)) {
            movement.x = -1;
        }
        if (input.isKeyPressed(DOWN) || input.isKeyPressed(S)) {
            movement.y = -1;
        }
        if (input.isKeyPressed(RIGHT) || input.isKeyPressed(D)) {
            movement.x = 1;
        }
        return movement;
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
        for (var obstacle : obstacles) {
            obstacle.getObjectGraphics().dispose();
        }
        tank.getObjectGraphics().dispose();
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
