package ru.mipt.bit.platformer;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
<<<<<<< HEAD

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
=======

import ru.mipt.bit.platformer.entity.level.Level;
import ru.mipt.bit.platformer.entity.playerinput.PlayerInput;
>>>>>>> 4fe93b7764387c3a3f40158528b2cb4ed6ee3efe

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.entity.draw.drawers.LevelDrawer;
import ru.mipt.bit.platformer.entity.objects.base.AbstractMovableLevelObject;
import ru.mipt.bit.platformer.playerinput.PlayerInput;
import ru.mipt.bit.platformer.playerinput.PlayerInputHandler;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

    public class GameDesktopLauncher implements ApplicationListener {
        private Level level;
        private LevelDrawer levelDrawer;

        @Override
        public void create() {
            List<AbstractMovableLevelObject> tanks = new ArrayList<>(Arrays.asList(
                    new Tank(new GridPoint2(1, 1))
            ));
            List<AbstractUnmovableLevelObject> trees = new ArrayList<>(Arrays.asList(
                    new Tree(new GridPoint2(1, 3)),
                    new Tree(new GridPoint2(2, 3))
            ));

            level = new Level(tanks, trees);
            levelDrawer = new LevelDrawer("level.tmx", new SpriteBatch(), level);
            levelDrawer.draw();
        }

        @Override
        public void render() {
            clearScreen();

            PlayerInputHandler.handle(level, PlayerInput.getAction());

            levelDrawer.renderMoves(Gdx.graphics.getDeltaTime());
            levelDrawer.recordDrawCommand();
        }

        public void clearScreen() {
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

            levelDrawer.dispose();
        }

        public static void main(String[] args) {
            Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
            // level width: 10 tiles x 128px, height: 8 tiles x 128px
            config.setWindowedMode(1280, 1024);
            new Lwjgl3Application(new GameDesktopLauncher(), config);
        }
    }
=======
    private Level level;
    private LevelDrawer levelDrawer;

    @Override
    public void create() {
        level = new Level();
        levelDrawer = new LevelDrawer("level.tmx", new SpriteBatch(), level);
        levelDrawer.draw();
    }

    @Override
    public void render() {
        clearScreen();
        level.moveTank(PlayerInput.chooseDirection());
        levelDrawer.renderMoves(Gdx.graphics.getDeltaTime());
        levelDrawer.recordDrawCommand();
    }

    public void clearScreen() {
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
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        levelDrawer.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
>>>>>>> 4fe93b7764387c3a3f40158528b2cb4ed6ee3efe
