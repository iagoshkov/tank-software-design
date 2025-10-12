package ru.mipt.bit.platformer;

import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.decrementedX;
import static ru.mipt.bit.platformer.util.GdxGameUtils.decrementedY;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.incrementedX;
import static ru.mipt.bit.platformer.util.GdxGameUtils.incrementedY;
import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;

import main.java.ru.mipt.bit.platformer.Direction;
import main.java.ru.mipt.bit.platformer.InputController;
import main.java.ru.mipt.bit.platformer.Tank;
import main.java.ru.mipt.bit.platformer.Tree;
import main.java.ru.mipt.bit.platformer.collision.CollisionDetector;
import main.java.ru.mipt.bit.platformer.collision.SimpleCollisionDetector;
import main.java.ru.mipt.config.GameConfig;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;


public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private Tank player;
    private Tree tree;
    private InputController inputController;
    private CollisionDetector collisionDetector;

    @Override
    public void create() {
        batch = new SpriteBatch();
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        
        collisionDetector = new SimpleCollisionDetector();

        // Создание объектов через абстракции
        player = new Tank(
            new Texture("images/tank_blue.png"),
            groundLayer,
            new GridPoint2(1, 1),
            collisionDetector,
            GameConfig.MOVEMENT_SPEED
        );
        
        tree = new Tree(
            new Texture("images/greenTree.png"), 
            groundLayer,
            new GridPoint2(1, 3)
        );    
        collisionDetector.addObstacle(tree);
        
        inputController = new InputController(player);
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        float deltaTime = Gdx.graphics.getDeltaTime();
        player.update(deltaTime, MOVEMENT_SPEED);
        inputController.handleInput(tree.getCoordinates());

        player.updateMovement(deltaTime, MOVEMENT_SPEED);
        levelRenderer.render();
        batch.begin();
        player.render(batch);
        tree.render(batch);
        batch.end();
    }
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
        player.dispose();
        tree.dispose();
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
