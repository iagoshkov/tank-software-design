package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.generator.LevelGenerator;
import ru.mipt.bit.platformer.movementCommand.*;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.objects.OnScreenObject;
import ru.mipt.bit.platformer.objects.Tank;

import java.util.ArrayList;
import java.util.Map;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {
    private static final float TANK_INITIAL_MOVEMENT_SPEED = 0.4f;
    private static final float BULLET_MOVEMENT_SPEED = 0.3f;
    GameGraphics gameGraphics;
    private final ArrayList<Tank> tanks = new ArrayList<>();
    private final ArrayList<OnScreenObject> obstacles = new ArrayList<>();
    private final ArrayList<Bullet> bullets = new ArrayList<>();

    @Override
    public void create() {
        gameGraphics = new GameGraphics();

        String levelLayout = "level.txt";
        LevelGenerator generator = LevelGenerator.getLevelGenerator(levelLayout,
                gameGraphics.getFieldWidth(), gameGraphics.getFieldHeight());

        for (var coordinatePair : generator.getObstaclesCoordinates()) {
            var o = new OnScreenObject("images/greenTree.png", coordinatePair);
            obstacles.add(o);
        }
        for (var coordinatePair : generator.getPlayersCoordinates()) {
            var o = new Tank("images/tank_blue.png", coordinatePair, TANK_INITIAL_MOVEMENT_SPEED,
                    new GridPoint2(gameGraphics.getFieldWidth(), gameGraphics.getFieldHeight()));
            tanks.add(o);
        }
        tanks.get(tanks.size() - 1).setManuallyControlled(true);

        gameGraphics.addDrawableObjects(obstacles);
        gameGraphics.addDrawableObjects(tanks);
    }

    @Override
    public void render() {
        clearScreen();

        float deltaTime = Gdx.graphics.getDeltaTime();
        updateTanksPositions(deltaTime);
        updateBullets(deltaTime);

        gameGraphics.drawAllObjects();
    }

    private static void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private void updateTanksPositions(float deltaTime) {
        MovementCommand automaticMovement = new RandomMovementCommand();
//        AiMovementCommand aiMovement = new AiMovementCommand(obstacles, tanks, fieldWidth, fieldHeight);
        MovementCommand userInputMovement = new UserInputMovementCommand(Gdx.input);

        Map<Tank, TankAction> userControlledTanksActions = userInputMovement.getTankActions(obstacles, tanks,
                gameGraphics.getFieldWidth(), gameGraphics.getFieldHeight());
        Map<Tank, TankAction> autoControlledTanksActions = automaticMovement.getTankActions(obstacles, tanks,
                gameGraphics.getFieldWidth(), gameGraphics.getFieldHeight());

        updateTanksInMap(userControlledTanksActions, deltaTime);
        updateTanksInMap(autoControlledTanksActions, deltaTime);
    }

    private void updateTanksInMap(Map<Tank, TankAction> actions, float deltaTime) {
        for (var tank : actions.keySet()) {
            if (tank.isDead()) {
                gameGraphics.removeDrawableObject(tank);
                tanks.remove(tank);
                continue;
            }
            TankAction tankAction = actions.get((tank));
            Bullet bullet = tank.update(tankAction, obstacles, tanks, deltaTime);
            if (bullet != null) {
                bullets.add(bullet);
                gameGraphics.addDrawableObject(bullet);
            }
        }
    }

    private void updateBullets(float deltaTime) {
        ArrayList<Bullet> toRemove = new ArrayList<>();
        for (var bullet : bullets) {
            if (bullet.isDead()) {
                gameGraphics.removeDrawableObject(bullet);
                toRemove.add(bullet);
                continue;
            }
            bullet.update(obstacles, tanks, deltaTime, BULLET_MOVEMENT_SPEED);
        }
        for (var bullet : toRemove) {
            bullets.remove(bullet);
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
        gameGraphics.disposeAllDrawableObjects();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
