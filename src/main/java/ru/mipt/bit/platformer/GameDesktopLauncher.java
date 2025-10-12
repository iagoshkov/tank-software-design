package ru.mipt.bit.platformer;

import java.util.List;

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
import ru.mipt.bit.platformer.util.*;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private GameField gameField;
    private TankModel tankModel;
    private TankView tankView;
    private GameEntity tankEntity;
    private TreeModel treeModel;
    private TreeView treeView;
    private GameEntity treeEntity;
    private InputHandler inputHandler;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        // Создаем игровое поле
        gameField = new GameField(levelRenderer, groundLayer);

        // Создаем танк
        Texture blueTankTexture = new Texture("images/tank_blue.png");
        TextureRegion playerGraphics = new TextureRegion(blueTankTexture);
        GridPoint2 initialTankPosition = new GridPoint2(1, 1);
        tankModel = new TankModel(initialTankPosition, 0.4f);
        tankView = new TankView(tankModel, playerGraphics, groundLayer, tileMovement);
        tankEntity = new GameEntity(tankModel, tankView);
        gameField.addGameEntity(tankEntity);

        // Создаем дерево
        Texture greenTreeTexture = new Texture("images/greenTree.png");
        TextureRegion treeGraphics = new TextureRegion(greenTreeTexture);
        GridPoint2 treePosition = new GridPoint2(1, 3);
        treeModel = new TreeModel(treePosition);
        treeView = new TreeView(treeModel, treeGraphics, groundLayer);
        treeEntity = new GameEntity(treeModel, treeView);
        gameField.addGameEntity(treeEntity);

        // Обработчик ввода от пользователя
        inputHandler = new InputHandler(tankModel, gameField);
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        inputHandler.handleInput(deltaTime);

        gameField.updateGameObjects(deltaTime);

        gameField.renderLevel();

        batch.begin();

        gameField.renderGameObjects(batch);

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
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
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
