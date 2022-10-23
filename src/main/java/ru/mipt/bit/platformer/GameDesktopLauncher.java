package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
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
import ru.mipt.bit.platformer.generator.LevelGenerator;
import ru.mipt.bit.platformer.movementCommand.AiMovementCommand;
import ru.mipt.bit.platformer.movementCommand.MovementCommand;
import ru.mipt.bit.platformer.movementCommand.RandomMovementCommand;
import ru.mipt.bit.platformer.movementCommand.UserInputMovementCommand;
import ru.mipt.bit.platformer.objects.OnScreenObject;
import ru.mipt.bit.platformer.objects.Player;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.Map;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {
    private static final float MOVEMENT_SPEED = 0.4f;
    private Batch batch;
    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;
    private int fieldWidth, fieldHeight;
    private final ArrayList<Player> tanks = new ArrayList<>();
    private final ArrayList<OnScreenObject> obstacles = new ArrayList<>();

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        fieldWidth = groundLayer.getWidth();
        fieldHeight = groundLayer.getHeight();

        String levelLayout = "level.txt";
        LevelGenerator generator = LevelGenerator.getLevelGenerator(levelLayout, fieldWidth, fieldHeight);


        for (var coordinatePair : generator.getObstaclesCoordinates()) {
            var o = new OnScreenObject("images/greenTree.png", coordinatePair);
            obstacles.add(o);
            moveRectangleAtTileCenter(groundLayer, o.getObjectGraphics().getRectangle(), o.getCoordinates());
        }

        for (var coordinatePair : generator.getPlayersCoordinates()) {
            var o = new Player("images/tank_blue.png", coordinatePair, new GridPoint2(fieldWidth, fieldHeight));
            tanks.add(o);
            moveRectangleAtTileCenter(groundLayer, o.getObjectGraphics().getRectangle(), o.getCoordinates());
        }
        tanks.get(tanks.size() - 1).setManuallyControlled(true);
    }

    @Override
    public void render() {
        // clear screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        float deltaTime = Gdx.graphics.getDeltaTime();

        levelRenderer.render();
        batch.begin();

        updateTanksPositions(deltaTime);
        drawObjects(deltaTime);
        batch.end();
    }

    private void updateTanksPositions(float deltaTime) {
//        MovementCommand automaticMovement = new RandomMovementCommand();
        AiMovementCommand automaticMovement = new AiMovementCommand(obstacles, tanks, fieldWidth, fieldHeight);
        MovementCommand userInputMovement = new UserInputMovementCommand(Gdx.input);

        Map<Player, GridPoint2> manuallyControlledMovements = userInputMovement.getTankActions(obstacles, tanks, fieldWidth, fieldHeight);
        Map<Player, GridPoint2> autoControlledMovements = automaticMovement.getTankActions(obstacles, tanks, fieldWidth, fieldHeight);

        updateTanksOfSingleType(manuallyControlledMovements, deltaTime);
        updateTanksOfSingleType(autoControlledMovements, deltaTime);
    }

    private void updateTanksOfSingleType(Map<Player, GridPoint2> movementsList, float deltaTime) {
        for (var tank : movementsList.keySet()) {
            tileMovement.moveRectangleBetweenTileCenters(tank.getObjectGraphics().getRectangle(), tank.getCoordinates(),
                    tank.getDestinationCoordinates(), tank.getMovementProgress());
            GridPoint2 movementCoordinates = movementsList.get((tank));
            tank.update(movementCoordinates, obstacles, tanks, deltaTime, MOVEMENT_SPEED);
        }
    }

    private void drawObjects(float deltaTime) {
        for (var obstacle : obstacles) {
            obstacle.draw(batch);
        }
        for (var tank : tanks) {
            tank.draw(batch);
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
        for (var obstacle : obstacles) {
            obstacle.getObjectGraphics().dispose();
        }
        for (var tank : tanks) {
            tank.getObjectGraphics().dispose();
        }
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
