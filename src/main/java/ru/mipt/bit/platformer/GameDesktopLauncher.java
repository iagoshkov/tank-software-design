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
import ru.mipt.bit.platformer.graphics.PlayerGraphics;
import ru.mipt.bit.platformer.graphics.TreeGraphics;
import ru.mipt.bit.platformer.movement.PlayerMovement;
import ru.mipt.bit.platformer.models.Player;
import ru.mipt.bit.platformer.models.Tree;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private Texture blueTankTexture;
    private Player player;
    private PlayerGraphics playerView;

    private Texture greenTreeTexture;

    private final List<Tree> obstacles = new ArrayList<>();
    private final List<TreeGraphics> obstacleViews = new ArrayList<>();

    private PlayerMovement playerInputHandler;

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
        player = new Player(tileMovement, new GridPoint2(1, 1));
        playerView = new PlayerGraphics(player, blueTankTexture);

        greenTreeTexture = new Texture("images/greenTree.png");

        addTree(new GridPoint2(1, 3));
        addTree(new GridPoint2(4, 4));
        addTree(new GridPoint2(6, 2));

        playerInputHandler = new PlayerMovement(player, getObstacleCoords());
    }

    private void addTree(GridPoint2 coords) {
        Tree tree = new Tree(tileMovement, coords);
        TreeGraphics treeView = new TreeGraphics(tree, greenTreeTexture);
        obstacles.add(tree);
        obstacleViews.add(treeView);
    }


    private List<GridPoint2> getObstacleCoords() {
        List<GridPoint2> obstacleCoords = new ArrayList<>();
        for (Tree tree : obstacles) {
            obstacleCoords.add(tree.getCoordinates());
        }
        return obstacleCoords;
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        playerInputHandler.handleInput(deltaTime);
        player.update(deltaTime);

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        playerView.render(batch);

        // render tree obstacles
        for (TreeGraphics treeView : obstacleViews) {
            treeView.render(batch);
        }

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
