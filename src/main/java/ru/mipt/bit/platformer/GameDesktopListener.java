package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.models.Player;
import ru.mipt.bit.platformer.models.graphics.basic.GameGraphicObject;
import ru.mipt.bit.platformer.models.graphics.basic.GameGraphicObjectFactory;
import ru.mipt.bit.platformer.models.graphics.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawGameGraphicObjectsUnscaled;


public class GameDesktopListener implements ApplicationListener {
    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private Tank tank;

    private Player player;
    private final List<GameGraphicObject> gameGraphicObjects = new ArrayList<>();

    @Override
    public void create() {
        TiledMapTileLayer groundLayer = initTiledMapTileLayer();
        initGameObjects(groundLayer);
    }

    @Override
    public void render() {
        clear();

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

//        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
//            if (isEqual(playerMovementProgress, 1f)) {
//                // check potential player destination for collision with obstacles
//                if (!treeObstacleCoordinates.equals(incrementedY(playerCoordinates))) {
//                    playerDestinationCoordinates.y++;
//                    playerMovementProgress = 0f;
//                }
//                playerRotation = 90f;
//            }
//        }
//        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
//            if (isEqual(playerMovementProgress, 1f)) {
//                if (!treeObstacleCoordinates.equals(decrementedX(playerCoordinates))) {
//                    playerDestinationCoordinates.x--;
//                    playerMovementProgress = 0f;
//                }
//                playerRotation = -180f;
//            }
//        }
//        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
//            if (isEqual(playerMovementProgress, 1f)) {
//                if (!treeObstacleCoordinates.equals(decrementedY(playerCoordinates))) {
//                    playerDestinationCoordinates.y--;
//                    playerMovementProgress = 0f;
//                }
//                playerRotation = -90f;
//            }
//        }
//        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
//            if (isEqual(playerMovementProgress, 1f)) {
//                if (!treeObstacleCoordinates.equals(incrementedX(playerCoordinates))) {
//                    playerDestinationCoordinates.x++;
//                    playerMovementProgress = 0f;
//                }
//                playerRotation = 0f;
//            }
//        }
//
//        // calculate interpolated player screen coordinates
//        tileMovement.calculateMovableGameObjectCoordinates(tank);
//
//        playerMovementProgress = continueProgress(playerMovementProgress, deltaTime, MOVEMENT_SPEED);
//        if (isEqual(playerMovementProgress, 1f)) {
//            // record that the player has reached his/her destination
//            playerCoordinates.set(playerDestinationCoordinates);
//        }

        // render each tile of the level
        player.handleKeyEvent(Gdx.input, gameGraphicObjects);

        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render graphic objects
        drawGameGraphicObjectsUnscaled(batch, gameGraphicObjects, player);

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
        gameGraphicObjects.forEach(GameGraphicObject::dispose);
        player.getTank().dispose();

        level.dispose();
        batch.dispose();
    }

    private TiledMapTileLayer initTiledMapTileLayer() {
        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);

        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        return groundLayer;
    }

    private void initGameObjects(TiledMapTileLayer groundLayer) {
        tank = GameGraphicObjectFactory.createTank(
                new Texture("images/tank_blue.png"),
                new GridPoint2(1, 5), 0f
        );

        GameGraphicObject tree = GameGraphicObjectFactory.createGameGraphicObject(
                new Texture("images/greenTree.png"),
                new GridPoint2(1, 3)
        );

        gameGraphicObjects.add(tree);
        player = new Player(tank, tileMovement);

        moveRectangleAtTileCenter(groundLayer, tree.getRectangle(), tree.getCoordinates());
    }

    // clear the screen
    private void clear() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }
}
