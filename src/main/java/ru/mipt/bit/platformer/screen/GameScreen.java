package ru.mipt.bit.platformer.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import ru.mipt.bit.platformer.util.TileMovement;

import ru.mipt.bit.platformer.input.InputController;
import ru.mipt.bit.platformer.input.GdxKeyboardInputController;
import ru.mipt.bit.platformer.model.*;
import ru.mipt.bit.platformer.logic.GameLogic;
import ru.mipt.bit.platformer.collision.TileCollisionDetector;
import java.util.Set;
import java.util.HashSet;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameScreen implements Screen {

    private Batch batch;

    // Рендеринг
    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    // Текстуры и спрайты
    private Texture blueTankTexture;
    private TextureRegion playerGraphics;
    private Rectangle playerRectangle;

    private Texture greenTreeTexture;
    private TextureRegion treeObstacleGraphics;
    private Rectangle treeObstacleRectangle;

    // Игровая логика
    private InputController input;
    private World world;
    private GameLogic gameLogic;

    @Override
    public void show() {
        batch = new SpriteBatch();

        // Загрузка уровня
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        // Создание игрового мира
        TileGrid tileGrid = new TileGrid(groundLayer);

        Set<GridPoint2> obstacles = new HashSet<>();
        obstacles.add(new GridPoint2(1, 3)); // дерево

        Player player = new Player(new GridPoint2(1, 1));
        world = new World(player, obstacles, tileGrid);

        // Инициализация игровой логики
        gameLogic = new GameLogic(new TileCollisionDetector());
        input = new GdxKeyboardInputController();

        // Создание графических объектов
        blueTankTexture = new Texture("images/tank_blue.png");
        playerGraphics = new TextureRegion(blueTankTexture);
        playerRectangle = createBoundingRectangle(playerGraphics);

        greenTreeTexture = new Texture("images/greenTree.png");
        treeObstacleGraphics = new TextureRegion(greenTreeTexture);
        treeObstacleRectangle = createBoundingRectangle(treeObstacleGraphics);

        // Позиционирование препятствия
        moveRectangleAtTileCenter(groundLayer, treeObstacleRectangle, new GridPoint2(1, 3));
    }

    @Override
    public void render(float delta) {
        // Очистка экрана
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // Обработка ввода и игровой логики
        input.pollMove().ifPresent(direction -> gameLogic.processMoveCommand(world, direction));
        gameLogic.updateWorld(world, delta);

        // Обновление позиции для рендеринга
        Player player = world.getPlayer();
        tileMovement.moveRectangleBetweenTileCenters(
                playerRectangle,
                player.getCoordinates(),
                player.getDestinationCoordinates(),
                player.getMovementProgress()
        );

        // Рендеринг уровня
        levelRenderer.render();

        // Рендеринг игровых объектов
        batch.begin();
        drawTextureRegionUnscaled(batch, playerGraphics, playerRectangle, player.getRotation());
        drawTextureRegionUnscaled(batch, treeObstacleGraphics, treeObstacleRectangle, 0f);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // not used
    }

    @Override
    public void resume() {
        // not used
    }

    @Override
    public void hide() {
        // not used
    }

    @Override
    public void dispose() {
        greenTreeTexture.dispose();
        blueTankTexture.dispose();
        level.dispose();
        batch.dispose();
    }
}