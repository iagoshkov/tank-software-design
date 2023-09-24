package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {
    private Batch batch;
    private Level level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;
    private ObjectGraphics tankObjectGraphics;
    private ObjectGraphics treeObjectGraphics;

    private final KeyboardInputHandler inputHandler = new KeyboardInputHandler();
    @Override
    public void create() {
        batch = new SpriteBatch();
        level = new Level();

        levelRenderer = createSingleLayerMapRenderer(level.getMap(), batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level.getMap());
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        // set player initial position
        tankObjectGraphics = new ObjectGraphics("images/tank_blue.png");

        treeObjectGraphics = new ObjectGraphics("images/greenTree.png");

        treeObjectGraphics.moveRectangle(groundLayer, level.getTreeObstacle().getCoordinates());
    }

    @Override
    public void render() {
        clearScreen();

        float deltaTime = Gdx.graphics.getDeltaTime();
        Direction desiredDirection = inputHandler.handleKeystrokes();
        level.getTank().tryMove(level.getTreeObstacle(), desiredDirection);
        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(
                                tankObjectGraphics.getRectangle(),
                                level.getTank().getCoordinates(),
                                level.getTank().getDestinationCoordinates(),
                                level.getTank().getMovementProgress());

        continueTankProgress(deltaTime);
        // render each tile of the level
        renderGame();
    }

    private void continueTankProgress(float deltaTime) {
        float newMovementProgress = continueProgress(level.getTank().getMovementProgress(),
                                                    deltaTime, level.getTank().getSpeed());
        level.getTank().tryReachDestinationCoordinates(newMovementProgress);
    }
    private void renderGame() {
        levelRenderer.render();
        // start recording all drawing commands
        batch.begin();
        // render player
        tankObjectGraphics.draw(batch, level.getTank().getRotation());
        // render tree obstacle
        treeObjectGraphics.draw(batch, 0f);
        // submit all drawing requests
        batch.end();
    }

    private static void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
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
        tankObjectGraphics.dispose();
        treeObjectGraphics.dispose();
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
