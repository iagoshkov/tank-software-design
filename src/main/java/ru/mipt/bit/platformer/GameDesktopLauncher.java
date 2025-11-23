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

import main.java.ru.mipt.bit.platformer.Direction;
import main.java.ru.mipt.bit.platformer.InputController;
import main.java.ru.mipt.bit.platformer.Tank;
import main.java.ru.mipt.bit.platformer.Tree;
import main.java.ru.mipt.bit.platformer.collision.CollisionDetector;
import main.java.ru.mipt.bit.platformer.collision.SimpleCollisionDetector;
import main.java.ru.mipt.bit.platformer.level.LevelManager;
import main.java.ru.mipt.bit.platformer.observer.GameLevelObservable;
import main.java.ru.mipt.bit.platformer.render.GameRenderer;
import main.java.ru.mipt.config.GameConfig;
import ru.mipt.bit.platformer.controller.ShootCommand;
import ru.mipt.bit.platformer.controller.ToggleHealthDisplayCommand;
import ru.mipt.bit.platformer.util.TileMovement;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.mipt.bit.platformer.config.GameConfig;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;


public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;
    private static final boolean USE_RANDOM_LEVEL = false;

    private Batch batch;
    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private Tank player;
    private InputController inputController;
    private CollisionDetector collisionDetector;
    private LevelManager levelManager;
    private ShootCommand shootCommand;
    private GameLevelObservable levelObservable;
    private GameRenderer gameRenderer;
    private Texture bulletTexture;

    private AnnotationConfigApplicationContext springContext;

    @Override
    public void create() {
        // Инициализируем Spring контекст
        springContext = new AnnotationConfigApplicationContext(GameConfig.class);
        
        // Получаем бины из Spring контейнера
        batch = new SpriteBatch();
        level = springContext.getBean(TiledMap.class);
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        
        tileMovement = springContext.getBean(TileMovement.class);
        collisionDetector = springContext.getBean(CollisionDetector.class);
        
        levelManager = springContext.getBean(LevelManager.class);
        Texture bulletTexture = springContext.getBean(Texture.class);

        // Создаем игрока с использованием Spring бинов
        if (USE_RANDOM_LEVEL) {
            player = levelManager.createRandomLevel(0.2f, MOVEMENT_SPEED);
        } else {
            player = levelManager.createLevelFromFile("levels/level1.txt", MOVEMENT_SPEED);
        }

        // Инициализируем уровень
        levelManager.initializeLevel();

        // Получаем команды из Spring
        ToggleHealthDisplayCommand toggleHealthCommand = springContext.getBean(ToggleHealthDisplayCommand.class);
        ShootCommand shootCommand = new ShootCommand(player);

        // Создаем рендерер и регистрируем как наблюдателя
        gameRenderer = new GameRenderer(batch);
        GameLevelObservable levelObservable = springContext.getBean(GameLevelObservable.class);
        levelObservable.addObserver(gameRenderer);

        // Создаем контроллер с Spring бинами
        inputController = new InputController(player, toggleHealthCommand, shootCommand);
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        float deltaTime = Gdx.graphics.getDeltaTime();
        
        inputController.handleInput();
        shootCommand.execute();
        toggleHealthCommand.execute();

        player.update(deltaTime);
        levelManager.updateTanks(deltaTime);
        collisionDetector.checkBulletCollisions();

        levelRenderer.render();
        batch.begin();
        gameRenderer.render(); // Отрисовываем все объекты через наблюдателя
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
        // Закрываем Spring контекст
        if (springContext != null) {
            springContext.close();
        }
        
        levelManager.dispose();
        if (gameRenderer != null) {
            gameRenderer.dispose();
        }
        bulletTexture.dispose();
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
