package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import ru.mipt.bit.platformer.controller.EntityController;
import ru.mipt.bit.platformer.controller.LevelController;
import ru.mipt.bit.platformer.controller.ObstacleController;
import ru.mipt.bit.platformer.controller.PlayerController;

import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;

public class GameDesktopLauncher implements ApplicationListener {
    private static final float MOVEMENT_SPEED = 0.4f;

    private PlayerInputController playerInputController;

    private Batch batch;

    private List<ObstacleController> obstacleControllers;
    private LevelController levelController;
    private PlayerController playerController;

    @Override
    public void create() {
        batch = new SpriteBatch();

        TiledMap map = new TmxMapLoader().load("level.tmx");
        EntityControllerFactory entityControllerFactory = new EntityControllerFactory(map);

        playerInputController = new PlayerInputController();

        obstacleControllers = List.of(
                entityControllerFactory.createObstacleController(
                        "images/green_tree.png", 1, 3
                )
        );
        levelController = LevelController.getInstance(
                obstacleControllers,
                map,
                createSingleLayerMapRenderer(map, batch)
        );
        playerController = entityControllerFactory.createPlayerController(
                "images/tank_blue.png", 1, 1, MOVEMENT_SPEED
        );
    }

    @Override
    public void render() {
        clearScreen();

        float delta = getTimePassedSinceLastRender();

        playerInputController.getDirection().ifPresent(dir -> playerController.move(dir, levelController));

        playerController.update(delta);

        levelController.render();
        batch.begin();
        obstacleControllers.forEach(obstacleController -> obstacleController.render(batch));
        playerController.render(batch);
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

    /**
     * Dispose of all the native resources
     * (classes which implement com.badlogic.gdx.utils.Disposable).
     */
    @Override
    public void dispose() {
        batch.dispose();
        obstacleControllers.forEach(EntityController::dispose);
        levelController.dispose();
        playerController.dispose();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private float getTimePassedSinceLastRender() {
        return Gdx.graphics.getDeltaTime();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
