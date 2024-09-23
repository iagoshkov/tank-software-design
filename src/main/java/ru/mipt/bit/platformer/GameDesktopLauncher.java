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
import ru.mipt.bit.platformer.core.Tank;
import ru.mipt.bit.platformer.core.Tree;
import ru.mipt.bit.platformer.ui.TankDrawable;
import ru.mipt.bit.platformer.ui.TreeDrawable;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;

    private Texture blueTankTexture;
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)

    private Texture greenTreeTexture;

    private Tank tank;
    private TankDrawable tankDrawable;
    private TreeDrawable treeDrawable;
    private InputHandler inputHandler;

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
        TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        // Texture decodes an image file and loads it into GPU memory, it represents a native resource
        blueTankTexture = new Texture("images/tank_blue.png");
        // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture

        greenTreeTexture = new Texture("images/greenTree.png");

        tank = new Tank();
        Tree tree = new Tree(new GridPoint2(1, 3), 0f);
        tankDrawable = new TankDrawable(tank, blueTankTexture, tileMovement);
        treeDrawable = new TreeDrawable(tree, greenTreeTexture, tileMovement);
        moveRectangleAtTileCenter(groundLayer, treeDrawable.getRectangle(), tree.getCoordinates());
        inputHandler = new InputHandler(tank, tree);
    }

    @Override
    public void render() {
        clearScreen();

        processInputs();

        processPlayerMovementProgress(getDeltaTime());

        renderEachTileOfLevel();

        recordAllDrawingCommands();
    }

    private void processInputs() {
        inputHandler.handleInputs();
    }

    private void recordAllDrawingCommands() {
        // start recording all drawing commands
        batch.begin();

        // render player
        tankDrawable.drawTexture(batch);

        // render tree obstacle
        treeDrawable.drawTexture(batch);

        // submit all drawing requests
        batch.end();
    }

    private void renderEachTileOfLevel() {
        levelRenderer.render();
    }

    private void processPlayerMovementProgress(float deltaTime) {
        tank.processMovementProgress(deltaTime);
    }

    private float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }

    private void clearScreen() {
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
        greenTreeTexture.dispose();
        blueTankTexture.dispose();
        level.dispose();
        batch.dispose();
    }
}