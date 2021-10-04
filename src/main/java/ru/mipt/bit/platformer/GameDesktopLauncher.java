package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import ru.mipt.bit.platformer.graphics.ObstacleGraphics;
import ru.mipt.bit.platformer.graphics.PlayerGraphics;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Player;
import ru.mipt.bit.platformer.util.TileMovement;
import com.badlogic.gdx.Input.Keys;

import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {
    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;

    private Player player;
    private PlayerGraphics playerGraphics;

    private List<Obstacle> obstacles;
    private ObstacleGraphics obstacleGraphics;

    private static final List<Integer> INPUT_KEYS = List.of(
            Keys.RIGHT, Keys.UP, Keys.LEFT, Keys.DOWN, Keys.W, Keys.A, Keys.S, Keys.D);

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

        player = new Player(new GridPoint2(1, 1), 0);
        playerGraphics = new PlayerGraphics(new Texture("images/tank_blue.png"), player,
                new TileMovement(groundLayer, Interpolation.smooth));

        obstacles = List.of(new Obstacle(new GridPoint2(1, 3), 0f));
        obstacleGraphics = new ObstacleGraphics(new Texture("images/greenTree.png"), obstacles, groundLayer);
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        for (var key : INPUT_KEYS) {
            if (Gdx.input.isKeyPressed(key)) {
                player.tryRotateAndStartMovement(calcDirectionFromInputKey(key), obstacles);
            }
        }

        playerGraphics.move();
        player.makeProgress(deltaTime);
        player.tryFinishMovement();

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        playerGraphics.draw(batch);

        // render obstacles
        obstacleGraphics.draw(batch);

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
        playerGraphics.dispose();
        obstacleGraphics.dispose();
        level.dispose();
        batch.dispose();
    }

    private static Direction calcDirectionFromInputKey(int key) {
        if (key == Keys.RIGHT || key == Input.Keys.D) {
            return Direction.RIGHT;
        }
        if (key == Input.Keys.UP || key == Input.Keys.W) {
            return Direction.UP;
        }
        if (key == Input.Keys.LEFT || key == Input.Keys.A) {
            return Direction.LEFT;
        }
        if (key == Input.Keys.DOWN || key == Input.Keys.S) {
            return Direction.DOWN;
        }
        throw new IllegalArgumentException();
    }
}
