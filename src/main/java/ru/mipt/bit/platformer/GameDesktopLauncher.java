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
import ru.mipt.bit.platformer.graphics.*;
import ru.mipt.bit.platformer.movement.*;
import ru.mipt.bit.platformer.models.*;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private Batch batch;
    private Renderer renderer;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private Texture blueTankTexture;
    private Texture greenTreeTexture;

    private Player player;
    private EntityGraphics playerView;

    private final List<Tree> obstacles = new ArrayList<>();
    private final List<EntityGraphics> obstacleViews = new ArrayList<>();

    private PlayerMovement playerInputHandler;

    @Override
    public void create() {
        batch = new SpriteBatch();
        renderer = new LibGdxRenderer(batch);

        // Загрузка карты
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        // Игрок
        blueTankTexture = new Texture("images/tank_blue.png");
        player = new Player(tileMovement, new GridPoint2(1, 1));
        playerView = new EntityGraphics(player, blueTankTexture, renderer);

        // Препятствия (деревья)
        greenTreeTexture = new Texture("images/greenTree.png");
        addTree(new GridPoint2(1, 3));
        addTree(new GridPoint2(4, 4));
        addTree(new GridPoint2(6, 2));

        // Контроллер ввода
        InputController inputController = new GdxInputController();
        playerInputHandler = new PlayerMovement(player, getObstacleCoords(), inputController);
    }

    private void addTree(GridPoint2 coords) {
        Tree tree = new Tree(tileMovement, coords);
        EntityGraphics treeView = new EntityGraphics(tree, greenTreeTexture, renderer);
        obstacles.add(tree);
        obstacleViews.add(treeView);
    }

    private List<GridPoint2> getObstacleCoords() {
        List<GridPoint2> coords = new ArrayList<>();
        for (Tree tree : obstacles) {
            coords.add(tree.getCoordinates());
        }
        return coords;
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private void handleInput(float deltaTime) {
        playerInputHandler.handleInput(deltaTime);
        player.update(deltaTime);
    }

    private void drawObjects(){
        batch.begin();
        playerView.render();
        for (EntityGraphics treeView : obstacleViews) {
            treeView.render();
        }
        batch.end();
    }

    @Override
    public void render() {
        clearScreen();

        float deltaTime = Gdx.graphics.getDeltaTime();

        handleInput(deltaTime);

        levelRenderer.render();

        drawObjects();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        greenTreeTexture.dispose();
        blueTankTexture.dispose();
        level.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
