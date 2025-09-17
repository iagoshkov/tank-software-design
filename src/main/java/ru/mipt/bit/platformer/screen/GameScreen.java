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

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameScreen implements Screen {

    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private Texture blueTankTexture;
    private TextureRegion playerGraphics;
    private Rectangle playerRectangle;
    private GridPoint2 playerCoordinates;
    private GridPoint2 playerDestinationCoordinates;
    private float playerMovementProgress = 1f;
    private float playerRotation;

    private Texture greenTreeTexture;
    private TextureRegion treeObstacleGraphics;
    private GridPoint2 treeObstacleCoordinates = new GridPoint2();
    private Rectangle treeObstacleRectangle = new Rectangle();

    @Override
    public void show() {
        batch = new SpriteBatch();

        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        blueTankTexture = new Texture("images/tank_blue.png");
        playerGraphics = new TextureRegion(blueTankTexture);
        playerRectangle = createBoundingRectangle(playerGraphics);
        playerDestinationCoordinates = new GridPoint2(1, 1);
        playerCoordinates = new GridPoint2(playerDestinationCoordinates);
        playerRotation = 0f;

        greenTreeTexture = new Texture("images/greenTree.png");
        treeObstacleGraphics = new TextureRegion(greenTreeTexture);
        treeObstacleCoordinates = new GridPoint2(1, 3);
        treeObstacleRectangle = createBoundingRectangle(treeObstacleGraphics);
        moveRectangleAtTileCenter(groundLayer, treeObstacleRectangle, treeObstacleCoordinates);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            if (isEqual(playerMovementProgress, 1f)) {
                if (!treeObstacleCoordinates.equals(incrementedY(playerCoordinates))) {
                    playerDestinationCoordinates.y++;
                    playerMovementProgress = 0f;
                }
                playerRotation = 90f;
            }
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            if (isEqual(playerMovementProgress, 1f)) {
                if (!treeObstacleCoordinates.equals(decrementedX(playerCoordinates))) {
                    playerDestinationCoordinates.x--;
                    playerMovementProgress = 0f;
                }
                playerRotation = -180f;
            }
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            if (isEqual(playerMovementProgress, 1f)) {
                if (!treeObstacleCoordinates.equals(decrementedY(playerCoordinates))) {
                    playerDestinationCoordinates.y--;
                    playerMovementProgress = 0f;
                }
                playerRotation = -90f;
            }
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            if (isEqual(playerMovementProgress, 1f)) {
                if (!treeObstacleCoordinates.equals(incrementedX(playerCoordinates))) {
                    playerDestinationCoordinates.x++;
                    playerMovementProgress = 0f;
                }
                playerRotation = 0f;
            }
        }

        tileMovement.moveRectangleBetweenTileCenters(playerRectangle, playerCoordinates, playerDestinationCoordinates, playerMovementProgress);

        playerMovementProgress = continueProgress(playerMovementProgress, delta, MOVEMENT_SPEED);
        if (isEqual(playerMovementProgress, 1f)) {
            playerCoordinates.set(playerDestinationCoordinates);
        }

        levelRenderer.render();

        batch.begin();
        drawTextureRegionUnscaled(batch, playerGraphics, playerRectangle, playerRotation);
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