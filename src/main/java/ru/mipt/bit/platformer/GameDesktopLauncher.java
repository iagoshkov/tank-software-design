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

import java.util.List;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {
    private LevelRenderer levelRenderer;
    private PhysicsController physicsController;

    private Player player;
    private List<Obstacle> obstacles;

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }

    private void createPlayer(GridPoint2 initialCoordinates, float initialRotation) {
        player = new Player(initialCoordinates, initialRotation);
    }

    private void createObstacle(GridPoint2 coordinates, float rotation) {
        obstacles = List.of(new Obstacle(coordinates, rotation));
    }

    @Override
    public void create() {
        TiledMap map = new TmxMapLoader().load("level.tmx");
        TiledMapTileLayer tileLayer = getSingleLayer(map);

        createPlayer(new GridPoint2(1, 1), 0);
        PlayerGraphics playerGraphics = new PlayerGraphics(new Texture("images/tank_blue.png"), player,
                new TileMovement(tileLayer, Interpolation.smooth));

        createObstacle(new GridPoint2(1, 3), 0);
        ObstacleGraphics obstacleGraphics = new ObstacleGraphics(new Texture("images/greenTree.png"),
                obstacles, tileLayer);

        physicsController = new PhysicsController(player, obstacles);
        levelRenderer = new LevelRenderer(map, playerGraphics, obstacleGraphics);
    }

    @Override
    public void render() {
        physicsController.process();
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
