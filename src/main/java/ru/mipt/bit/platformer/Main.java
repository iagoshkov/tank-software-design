package ru.mipt.bit.platformer;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        GameLauncher gameLauncher = new GameDesktopLauncher();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(GameConfig.windowWidth, GameConfig.windowHeight);
        new Lwjgl3Application(gameLauncher, config);
    }
}
