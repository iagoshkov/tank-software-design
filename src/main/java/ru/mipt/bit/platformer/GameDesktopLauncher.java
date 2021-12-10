package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.driver.GameDriver;
import ru.mipt.bit.platformer.generators.LevelGenerator;
import ru.mipt.bit.platformer.generators.RandomGenerator;
import ru.mipt.bit.platformer.graphics.Drawer;
import ru.mipt.bit.platformer.driver.LogicLevel;

public class GameDesktopLauncher implements ApplicationListener {

    private LogicLevel logicLevel;
    private GameDriver gameDriver;


    @Override
    public void create() {
        LevelGenerator levelGenerator = new RandomGenerator();

        logicLevel = levelGenerator.getLevel();
        gameDriver = new GameDriver(logicLevel);
        gameDriver.createManagerForPlayer();
        gameDriver.createManagerForTanks();
        
    }

    @Override
    public void render() {
        // clear the screen
        Drawer.clearScreen();

        gameDriver.generateCommands();
        gameDriver.updateObjects();

        logicLevel.levelRenderer.render(logicLevel.getTanks(), logicLevel.getBullets());
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
        logicLevel.levelRenderer.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
