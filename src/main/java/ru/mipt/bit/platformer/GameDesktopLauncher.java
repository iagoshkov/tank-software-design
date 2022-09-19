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

import com.badlogic.gdx.math.Interpolation;


import ru.mipt.bit.platformer.util.objects.Player;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.util.objects.Tree;

import java.util.HashMap;

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

    Player player = new Player();
    Tree tree = new Tree();




    @Override
    public void create() {
        batch = new SpriteBatch();

        player.createPlayerObject();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        tree.createTreeObject(groundLayer);

    }

    @Override
    public void render() {

        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            moving(UP);
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            moving(LEFT);
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            moving(DOWN);
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            moving(RIGHT);
        }

        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(player.playerRectangle, player.playerCoordinates, player.playerDestinationCoordinates, player.playerMovementProgress);

        player.playerMovementProgress = continueProgress(player.playerMovementProgress, deltaTime, MOVEMENT_SPEED);
        if (isEqual(player.playerMovementProgress, 1f)) {
            // record that the player has reached his/her destination
            player.playerCoordinates.set(player.playerDestinationCoordinates);
        }

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        drawTextureRegionUnscaled(batch, player.playerGraphics, player.playerRectangle, player.playerRotation);

        // render tree obstacle
        drawTextureRegionUnscaled(batch, tree.treeObstacleGraphics, tree.treeObstacleRectangle, 0f);

        // submit all drawing requests
        batch.end();
    }



    public static final int DOWN = 20;
    public static final int LEFT = 21;
    public static final int RIGHT = 22;
    public static final int UP = 19;



    public void getDestinationCoordinates(int key){
        if  (key == UP) {
            player.playerDestinationCoordinates.y++;
        }
        if  (key == LEFT) {
            player.playerDestinationCoordinates.x--;
        }
        if  (key == DOWN) {
            player.playerDestinationCoordinates.y--;
        }
        if  (key == RIGHT) {
            player.playerDestinationCoordinates.x++;
        }
    }

    public void moving(int key){

        HashMap<Integer, Float> moves = new HashMap<>();
        moves.put(UP, 90f);
        moves.put(DOWN, -90f);
        moves.put(LEFT, -180f);
        moves.put(RIGHT, 0f);



        if (isEqual(player.playerMovementProgress, 1f)) {
            // check potential player destination for collision with obstacles
            if (!tree.treeObstacleCoordinates.equals(incrementedY(player.playerCoordinates))) {
                getDestinationCoordinates(key);
                player.playerMovementProgress = 0f;
            }
        }
        player.playerRotation = moves.get(key);

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
        tree.greenTreeTexture.dispose();
        player.blueTankTexture.dispose();
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
