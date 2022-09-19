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
import ru.mipt.bit.platformer.util.Rotation;
import ru.mipt.bit.platformer.util.Tank;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.util.Tree;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;
    private Tank tank;
    private Tree tree;
    private Batch batch;
    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        tank = new Tank(new Texture("images/tank_blue.png"), new GridPoint2(1, 1));
        tree = new Tree(new Texture("images/greenTree.png"), new GridPoint2(1, 3));
        moveRectangleAtTileCenter(groundLayer, tree.getRectangle(), tree.getCoordinates());
    }

    @Override
    public void render() {
        // clear the screen
        clearTheScreen();

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        handlePressedButton();

        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(tank.getRectangle(), tank.coordinates, tank.destinationCoordinates, tank.getMovementProgress());

        tank.setMovementProgress(continueProgress(tank.getMovementProgress(), deltaTime, MOVEMENT_SPEED));
        if (isEqual(tank.getMovementProgress(), 1f)) {
            // record that the player has reached his/her destination
            tank.coordinates.set(tank.destinationCoordinates);
        }

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        drawTextureRegionUnscaled(batch, tank.getGraphics(), tank.getRectangle(), tank.getRotation().toFloat());

        // render tree obstacle
        drawTextureRegionUnscaled(batch, tree.getRegion(), tree.getRectangle(), 0f);

        // submit all drawing requests
        batch.end();
    }

    private static void clearTheScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private void handlePressedButton() {
        if (isEqual(tank.getMovementProgress(), 1f)) handlePressedButtonByStationaryTank();
    }

    private void handlePressedButtonByStationaryTank() {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            // check potential player destination for collision with obstacles
            if (!tree.getCoordinates().equals(incrementedY(tank.coordinates))) {
                tank.destinationCoordinates.y++;
                tank.setMovementProgress(0f);
            }
            tank.setRotation(Rotation.UP);
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            if (!tree.getCoordinates().equals(decrementedX(tank.coordinates))) {
                tank.destinationCoordinates.x--;
                tank.setMovementProgress(0f);
            }
            tank.setRotation(Rotation.LEFT);
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            if (!tree.getCoordinates().equals(decrementedY(tank.coordinates))) {
                tank.destinationCoordinates.y--;
                tank.setMovementProgress(0f);
            }
            tank.setRotation(Rotation.DOWN);
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            if (!tree.getCoordinates().equals(incrementedX(tank.coordinates))) {
                tank.destinationCoordinates.x++;
                tank.setMovementProgress(0f);
            }
            tank.setRotation(Rotation.RIGHT);
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
        tree.dispose();
        tank.dispose();
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
