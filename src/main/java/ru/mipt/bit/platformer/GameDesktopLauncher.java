package ru.mipt.bit.platformer;

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
import org.w3c.dom.css.Rect;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    public class Player {
        private final TextureRegion playerGraphics;
        private final Rectangle playerRectangle;
        // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
        private final GridPoint2 playerCoordinates;
        // which tile the player want to go next
        private final GridPoint2 playerDestinationCoordinates;
        private float playerMovementProgress = 1f;
        private float playerRotation;

        public Player() {
            playerGraphics = new TextureRegion(blueTankTexture);
            playerRectangle = createBoundingRectangle(playerGraphics);
            // set player initial position
            playerDestinationCoordinates = new GridPoint2(1, 1);
            playerCoordinates = new GridPoint2(playerDestinationCoordinates);
            playerRotation = 0f;
        }

        public void displace(float rotation, GridPoint2 displacement, GridPoint2 incrementedCoordinates, Tree tree) {
            if (isEqual(playerMovementProgress, 1f)) {
                // check potential player destination for collision with obstacles
                if (!tree.getTreeObstacleCoordinates().equals(incrementedCoordinates)) {
                    playerDestinationCoordinates.add(displacement);
                    playerMovementProgress = 0f;
                }

                playerRotation = rotation;
            }
        }

        public void performAction(Tree tree, float deltaTime) {
            if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
                displace(90f, new GridPoint2(0, 1), incrementedY(playerCoordinates), tree);
            }

            if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
                displace(-180f, new GridPoint2(-1, 0), decrementedX(playerCoordinates), tree);
            }

            if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
                displace(-90f, new GridPoint2(0, -1), decrementedY(playerCoordinates), tree);
            }

            if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
                displace(0f, new GridPoint2(1, 0), incrementedX(playerCoordinates), tree);
            }


            // calculate interpolated player screen coordinates
            tileMovement.moveRectangleBetweenTileCenters(playerRectangle, playerCoordinates, playerDestinationCoordinates, playerMovementProgress);

            playerMovementProgress = continueProgress(playerMovementProgress, deltaTime, MOVEMENT_SPEED);
            if (isEqual(playerMovementProgress, 1f)) {
                // record that the player has reached his/her destination
                playerCoordinates.set(playerDestinationCoordinates);
            }
        }

        public void render() {
            drawTextureRegionUnscaled(batch, playerGraphics, playerRectangle, playerRotation);
        }

    }

    public class Tree {
        private final Texture greenTreeTexture;
        private final TextureRegion treeObstacleGraphics;
        private final GridPoint2 treeObstacleCoordinates;
        private final Rectangle treeObstacleRectangle;

        public Tree(TiledMapTileLayer groundLayer) {
            greenTreeTexture = new Texture("images/greenTree.png");
            treeObstacleGraphics = new TextureRegion(greenTreeTexture);
            treeObstacleCoordinates = new GridPoint2(1, 3);
            treeObstacleRectangle = createBoundingRectangle(treeObstacleGraphics);
            moveRectangleAtTileCenter(groundLayer, treeObstacleRectangle, treeObstacleCoordinates);
        }

        public GridPoint2 getTreeObstacleCoordinates() {
            return treeObstacleCoordinates;
        }

        public void render() {
            drawTextureRegionUnscaled(batch, treeObstacleGraphics, treeObstacleRectangle, 0f);
        }

        public void disposeOfGreenTreeTexture() {
            greenTreeTexture.dispose();
        }

    }


    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private Texture blueTankTexture;

    private Player player;

    private Tree tree;



    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        // Texture decodes an image file and loads it into GPU memory, it represents a native resource
        blueTankTexture = new Texture("images/tank_blue.png");
        // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture

        player = new Player();
        tree = new Tree(groundLayer);
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        player.performAction(tree, deltaTime);

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        player.render();

        // render tree obstacle
        tree.render();

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
        tree.disposeOfGreenTreeTexture();
        blueTankTexture.dispose();
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
