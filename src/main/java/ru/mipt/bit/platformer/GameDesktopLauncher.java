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
import ru.mipt.bit.platformer.util.TileMovement;

import ru.mipt.bit.platformer.util.Coords;
import ru.mipt.bit.platformer.util.PlayObject;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    // player tank
    private PlayObject player;

    // tree
    private PlayObject tree;


    private void rotateTank(int var1, int var2, Coords cordChange, float rotation) {
        if (Gdx.input.isKeyPressed(var1) || Gdx.input.isKeyPressed(var2)) {
            if (isEqual(player.movementProgress, 1f)) {
                switch (cordChange) {
                    case XMORE:
                        if (!tree.initialCoordinates.equals(incrementedX(player.coordinates))) {
                            player.initialCoordinates.x++;
                            player.movementProgress = 0f;
                        }
                        break;
                    case XLESS:
                        if (!tree.initialCoordinates.equals(decrementedX(player.coordinates))) {
                            player.initialCoordinates.x--;
                            player.movementProgress = 0f;
                        }
                        break;
                    case YMORE:
                        if (!tree.initialCoordinates.equals(incrementedY(player.coordinates))) {
                            player.initialCoordinates.y++;
                            player.movementProgress = 0f;
                        }
                        break;
                    case YLESS:
                        if (!tree.initialCoordinates.equals(decrementedY(player.coordinates))) {
                            player.initialCoordinates.y--;
                            player.movementProgress = 0f;
                        }
                        break;
                }
                player.rotation = rotation;
            }
        }
    }

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        // player tank
        player = new PlayObject(new Texture("images/tank_blue.png"), new GridPoint2(1, 1), 0f);

        //tree
        tree = new PlayObject(new Texture("images/greenTree.png"), new GridPoint2(1, 3));
        moveRectangleAtTileCenter(groundLayer, tree.rectangle, tree.initialCoordinates);
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        rotateTank(UP, W, Coords.YMORE, 90f);
        rotateTank(LEFT, A, Coords.XLESS, -180f);
        rotateTank(DOWN, S, Coords.YLESS, -90f);
        rotateTank(RIGHT, D, Coords.XMORE, 0f);

        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(player.rectangle, player.coordinates,
                player.initialCoordinates, player.movementProgress);

        player.movementProgress = continueProgress(player.movementProgress, deltaTime, MOVEMENT_SPEED);
        if (isEqual(player.movementProgress, 1f)) {
            // record that the player has reached his/her destination
            player.coordinates.set(player.initialCoordinates);
        }

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        drawTextureRegionUnscaled(batch, player.graphics, player.rectangle, player.rotation);

        // render tree obstacle
        drawTextureRegionUnscaled(batch, tree.graphics, tree.rectangle, 0f);

        // submit all drawing requests
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
        tree.texture.dispose();
        player.texture.dispose();
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
