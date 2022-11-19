package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.generator.LevelGenerator;
import ru.mipt.bit.platformer.movementCommand.MovementCommand;
import ru.mipt.bit.platformer.movementCommand.RandomMovementCommand;
import ru.mipt.bit.platformer.movementCommand.TankAction;
import ru.mipt.bit.platformer.movementCommand.UserInputMovementCommand;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.objects.OnScreenObject;
import ru.mipt.bit.platformer.objects.Tank;

import java.util.ArrayList;
import java.util.Map;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {
    static final float TANK_INITIAL_MOVEMENT_SPEED = 0.4f;
    static final float BULLET_MOVEMENT_SPEED = 0.3f;
    GameGraphics gameGraphics;
    GameLogic gameLogic;

    @Override
    public void create() {
        gameGraphics = new GameGraphics();

        String levelLayout = "level.txt";
        LevelGenerator generator = LevelGenerator.getLevelGenerator(levelLayout,
                gameGraphics.getFieldWidth(), gameGraphics.getFieldHeight());

        gameLogic = new GameLogic(generator, gameGraphics);
    }

    @Override
    public void render() {
        clearScreen();
        gameLogic.tick(Gdx.graphics.getDeltaTime());
    }

    private static void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
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
