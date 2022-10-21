package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.Builder.IObstacleLevelBuilder;
import ru.mipt.bit.platformer.Builder.ObstacleLevelBuilder;
import ru.mipt.bit.platformer.Controllers.IControl;
import ru.mipt.bit.platformer.Controllers.TankControl;
import ru.mipt.bit.platformer.Entities.Tank;
import ru.mipt.bit.platformer.Entities.TextureObstacle;
import ru.mipt.bit.platformer.Obstacles.IObstacleInLevel;
import ru.mipt.bit.platformer.Render.LevelRender;

import java.util.Random;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;
    private Batch batch;
    private final LevelRender levelRender = LevelRender.getInstance();
    private Tank tank;
    private IObstacleInLevel obstacleInLevel;
    @Override
    public void create() {
        batch = new SpriteBatch();

        levelRender.setLevelMap("level.tmx");
        levelRender.setLayerRenderer(batch);
        levelRender.setLayerMovement("Ground", Interpolation.smooth);

        obstacleInLevel = createNObstacles(5);
        levelRender.setLet(obstacleInLevel);

        IControl tankControl = new TankControl();
        tank = new Tank("images/tank_blue.png", new GridPoint2(1, 1), 0f, tankControl);
    }

    @Override
    public void render() {
        clearTheScreen();

        tank.getInValues(Gdx.input, obstacleInLevel);
        calculatePlayerInterpolatedCoordinates();

        levelRender.render();

        batch.begin();

        tank.visualisation.draw(batch, tank.entity.rotation);

        for (TextureObstacle texture: obstacleInLevel.getObstacleEntities()) texture.obstacleVisualisation.draw(batch);

        batch.end();
    }

    private static void clearTheScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private IObstacleInLevel createNObstacles (int obstaclesAmount){
        IObstacleLevelBuilder obstacleBuilder = new ObstacleLevelBuilder();
        for (int i = 0; i < 5; i++){
            obstacleBuilder.addBush(new GridPoint2(new Random().nextInt(9) + 1, new Random().nextInt(5) + 1));
        }

        return obstacleBuilder.createBush();
    }

    private void calculatePlayerInterpolatedCoordinates() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        levelRender.mapMovements.get("Ground").moveRectangleBetweenTileCenters(
                tank.visualisation.rectangle,
                tank.entity.position,
                tank.entity.destinationPosition,
                tank.entity.movementProgress);
        tank.entity.movementProgress = continueProgress(tank.entity.movementProgress, deltaTime, MOVEMENT_SPEED);
        if (isEqual(tank.entity.movementProgress, 1f)) {
            tank.entity.position.set(tank.entity.destinationPosition);
        }
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
        levelRender.tiledMap.dispose();
        tank.visualisation.texture.dispose();
        for (TextureObstacle obstacle: obstacleInLevel.getObstacleEntities()) {
            obstacle.obstacleVisualisation.texture.dispose();
        }
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}