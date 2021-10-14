package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.graphics.LevelRenderer;
import ru.mipt.bit.platformer.graphics.ObstacleGraphics;
import ru.mipt.bit.platformer.graphics.PlayerGraphics;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Player;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.loaders.*;

import java.util.List;
import java.util.stream.Collectors;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {
    private final GameObjectMapLoader gameObjectMapLoader;

    private LevelRenderer levelRenderer;
    private PlayerController playerController;

    private Player player;
    private List<Obstacle> obstacles;

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        //
        var gameDesktopLauncher = new GameDesktopLauncher(new GameObjectMapFileLoader("/gameObjectMap"));
        new Lwjgl3Application(gameDesktopLauncher, config);
    }

    public GameDesktopLauncher(GameObjectMapLoader gameObjectMapLoader) {
        this.gameObjectMapLoader = gameObjectMapLoader;
    }

    private void createPlayer() {
        player = new Player(gameObjectMapLoader.getPlayerPosition(), 0);
    }

    private void createObstacles() {
        obstacles = gameObjectMapLoader.getTreePositions().stream()
                .map(position -> new Obstacle(position, 0))
                .collect(Collectors.toList());
    }

    @Override
    public void create() {
        TiledMap map = new TmxMapLoader().load("level.tmx");
        TiledMapTileLayer tileLayer = getSingleLayer(map);

        try {
            gameObjectMapLoader.loadMap();
        } catch (MapLoadingException e) {
            e.printStackTrace();
        }

        createPlayer();
        PlayerGraphics playerGraphics = new PlayerGraphics(new Texture("images/tank_blue.png"), player,
                new TileMovement(tileLayer, Interpolation.smooth));

        createObstacles();
        ObstacleGraphics obstacleGraphics = new ObstacleGraphics(new Texture("images/greenTree.png"),
                obstacles, tileLayer);

        playerController = new PlayerController(player, obstacles);
        levelRenderer = new LevelRenderer(map, playerGraphics, obstacleGraphics);
    }

    @Override
    public void render() {
        playerController.process();
        levelRenderer.render();
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
        levelRenderer.dispose();
    }
}
