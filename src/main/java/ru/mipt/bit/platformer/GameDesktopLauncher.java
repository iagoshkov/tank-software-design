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

import ru.mipt.bit.platformer.abstractions.Tank;
import ru.mipt.bit.platformer.abstractions.Tree;
import ru.mipt.bit.platformer.graphics.TankGraphics;
import ru.mipt.bit.platformer.graphics.TreeGraphics;
import ru.mipt.bit.platformer.util.TileMovement;


import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    
    private Texture blueTankTexture;
    private Texture greenTreeTexture;
    
    private Tank tank;
    private TankGraphics tankGraphics;
    private TreeGraphics treeGraphics;
    
    private MoveChecker moveChecker;

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
        greenTreeTexture = new Texture("images/greenTree.png");


        tank = new Tank();
        Tree tree = new Tree(new GridPoint2(1, 3), 0f);
        
	    tankGraphics = new TankGraphics(tank, blueTankTexture, tileMovement);
        treeGraphics = new TreeGraphics(tree, greenTreeTexture, tileMovement);
        
	    moveRectangleAtTileCenter(groundLayer, treeGraphics.getRectangle(), tree.getCoordinates());
        moveChecker = new MoveChecker(tank, tree);
    }

    private float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);   

        moveChecker.checkMoves();

        tank.processMovementProgress(getDeltaTime());
        
	    tankGraphics.drawMove();

        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        tankGraphics.drawTexture(batch);

        // render tree obstacle
        treeGraphics.drawTexture(batch); 

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
        greenTreeTexture.dispose();
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
