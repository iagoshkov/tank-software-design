package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
import ru.mipt.bit.platformer.Entities.MapObject;
import ru.mipt.bit.platformer.Entities.Movable;
import ru.mipt.bit.platformer.Entities.Tank;
import ru.mipt.bit.platformer.Entities.Tree;
import ru.mipt.bit.platformer.Instructions.Direction;
import ru.mipt.bit.platformer.Instructions.Instruction;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {
    private static final float DEFAULT_MOVEMENT_SPEED = 0.4f;

    private Batch batch;
    private TiledMap level;
    private MapRenderer levelRenderer;
    private TiledMapTileLayer groundLayer;
    private TileMovement tileMovement;

    private Tank playerTank;
    private final List<Movable> movableObjects = new ArrayList<>();
    private final List<MapObject> staticObjects = new ArrayList<>();
    private final List<List<? extends MapObject>> allObjects = new ArrayList<>(2);

    private final InputController inputController = new InputController();
    private final GraphicsController graphicsController = new GraphicsController();

    @Override
    public void create() {
        initKeyMappings();

        batch = new SpriteBatch();
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        playerTank = new Tank(new GridPoint2(2, 1), Direction.RIGHT, DEFAULT_MOVEMENT_SPEED);
        movableObjects.addAll(List.of(
                playerTank,
                new Tank(new GridPoint2(2, 4), Direction.UP, DEFAULT_MOVEMENT_SPEED),
                new Tank(new GridPoint2(1, 4), Direction.UP, DEFAULT_MOVEMENT_SPEED),
                new Tank(new GridPoint2(3, 4), Direction.UP, DEFAULT_MOVEMENT_SPEED)
        ));
        staticObjects.addAll(List.of(
                new Tree(new GridPoint2(1, 3)),
                new Tree(new GridPoint2(2, 3)),
                new Tree(new GridPoint2(3, 3)),
                new Tree(new GridPoint2(4, 4)),
                new Tree(new GridPoint2(0, 4))
        ));
        allObjects.add(movableObjects);
        allObjects.add(staticObjects);

        initGraphics();
        createStaticObjects();
    }

    private void createStaticObjects() {
        for (MapObject object : staticObjects) {
            moveRectangleAtTileCenter(groundLayer, graphicsController.getGraphics(object).getRectangle(), object.getCoordinates());
        }
    }

    private void initGraphics() {
        graphicsController.addGraphicsOf(staticObjects);
        graphicsController.addGraphicsOf(movableObjects);
    }

    private void initKeyMappings() {
        inputController.addMapping(Keys.UP, Direction.UP);
        inputController.addMapping(Keys.W, Direction.UP);
        inputController.addMapping(Keys.LEFT, Direction.LEFT);
        inputController.addMapping(Keys.A, Direction.LEFT);
        inputController.addMapping(Keys.DOWN, Direction.DOWN);
        inputController.addMapping(Keys.S, Direction.DOWN);
        inputController.addMapping(Keys.RIGHT, Direction.RIGHT);
        inputController.addMapping(Keys.D, Direction.RIGHT);
    }

    @Override
    public void render() {
        clearScreen();

        updateGameState(Gdx.graphics.getDeltaTime(), inputController.getInstruction());

        renderGame();
    }

    private static void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private void updateGameState(float deltaTime, Instruction instruction) {
        if (instruction instanceof Direction) {
            playerTank.moveIfNotCollides((Direction) instruction, allObjects.stream().flatMap(List::stream).collect(Collectors.toList()));
        }

        for (Movable object : movableObjects) {
            tileMovement.moveRectangleBetweenTileCenters(graphicsController.getGraphics(object).getRectangle(), object.getCoordinates(), object.getDestinationCoordinates(), object.getMovementProgress());
        }

        playerTank.updateState(deltaTime);
    }

    private void renderGame() {
        levelRenderer.render();

        batch.begin();

        drawObjects(staticObjects);
        drawObjects(movableObjects);

        batch.end();
    }

    private void drawObjects(List<? extends MapObject> objects) {
        for (MapObject object : objects) {
            graphicsController.drawObject(batch, object);
        }
    }

    @Override
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        graphicsController.dispose();
        level.dispose();
        batch.dispose();
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

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
