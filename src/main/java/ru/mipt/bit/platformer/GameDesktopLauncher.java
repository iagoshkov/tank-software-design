package ru.mipt.bit.platformer;

import java.io.IOException;
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

    private LevelGenerator levelGenerator;
    private boolean useRandomLevel = false; // true - случайный уровень, false - из файла
    private String levelFilePath = "src/main/resources/level.txt"; // путь к файлу уровня

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

        // Создаем генератор уровней (размер поля берем из groundLayer)
        int fieldWidth = groundLayer.getWidth();
        int fieldHeight = groundLayer.getHeight();
        levelGenerator = new LevelGenerator(fieldWidth, fieldHeight);

        // Генерируем или загружаем уровень
        LevelGenerator.LevelData levelData;
        if (useRandomLevel) {
            levelData = levelGenerator.generateRandomLevel(5); // 5 деревьев
        } else {
            try {
                levelData = levelGenerator.loadLevelFromFile(levelFilePath);
            } catch (IOException e) {
                // Если файл не найден, используем случайный уровень
                System.err.println("Не удалось загрузить уровень из файла: " + e.getMessage());
                levelData = levelGenerator.generateRandomLevel(5);
            }
        }

        // Загружаем текстуры
        Texture blueTankTexture = new Texture("images/tank_blue.png");
        Texture greenTreeTexture = new Texture("images/greenTree.png");

        // Инициализируем уровень
        gameField.initializeLevel(levelData, blueTankTexture, greenTreeTexture, tileMovement);

        // Получаем танк для обработчика ввода
        List<GameEntity> entities = gameField.getGameEntities();
        TankModel tankModel = null;
        for (GameEntity entity : entities) {
            if (entity.getModel() instanceof TankModel) {
                tankModel = (TankModel) entity.getModel();
                break;
            }
        }

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

    public void setUseRandomLevel(boolean useRandomLevel) {
        this.useRandomLevel = useRandomLevel;
    }

    public void setLevelFilePath(String levelFilePath) {
        this.levelFilePath = levelFilePath;
    }

    public void reloadLevel() {
        // Перезагружаем уровень
        Texture blueTankTexture = new Texture("images/tank_blue.png");
        Texture greenTreeTexture = new Texture("images/greenTree.png");

        LevelGenerator.LevelData levelData;
        if (useRandomLevel) {
            levelData = levelGenerator.generateRandomLevel(5);
        } else {
            try {
                levelData = levelGenerator.loadLevelFromFile(levelFilePath);
            } catch (IOException e) {
                System.err.println("Не удалось загрузить уровень из файла: " + e.getMessage());
                levelData = levelGenerator.generateRandomLevel(5);
            }
        }

        gameField.initializeLevel(levelData, blueTankTexture, greenTreeTexture, tileMovement);

        // Обновляем ссылку на танк в inputHandler
        List<GameEntity> entities = gameField.getGameEntities();
        TankModel tankModel = null;
        for (GameEntity entity : entities) {
            if (entity.getModel() instanceof TankModel) {
                tankModel = (TankModel) entity.getModel();
                break;
            }
        }
        inputHandler = new InputHandler(tankModel, gameField);
    }
}
