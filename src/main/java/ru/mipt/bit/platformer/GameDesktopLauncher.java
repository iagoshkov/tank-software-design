package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.mipt.bit.platformer.game.level.Level;
import ru.mipt.bit.platformer.game.level.LevelEntity;
import ru.mipt.bit.platformer.game.level.LevelEntityDatabase;
import ru.mipt.bit.platformer.game.level.LevelRenderer;
import ru.mipt.bit.platformer.game.player.Player;
import ru.mipt.bit.platformer.game.player.PlayerMove;
import ru.mipt.bit.platformer.game.player.PlayerMoveCoordinator;
import ru.mipt.bit.platformer.game.UserInput;

import java.util.List;

public class GameDesktopLauncher implements ApplicationListener {
    /*
    Класс, ответственный за инициализацию объектов
     */

    private LevelRenderer levelRenderer;
    private PlayerMoveCoordinator playerMoveCoordinator;
    private Player player;

    @Override
    public void create() {
        Level level = new Level("level.tmx");
        Batch batch = new SpriteBatch();

        LevelEntity blueTank = LevelEntityDatabase.getBlueTank();
        blueTank.setCoordinates(1, 1);

        LevelEntity greenTree = LevelEntityDatabase.getGreenTree();
        greenTree.setCoordinates(1, 3);

        player = new Player(blueTank);
//        player = new Player(greenTree);  // Можно двигаться кустом :)

        List<LevelEntity> obstacles = LevelEntityDatabase.createdObjects;  // Пока препятствия - все объекты
//        List<LevelObject> obstacles = Arrays.asList(greenTree);

        levelRenderer = new LevelRenderer(level, batch, LevelEntityDatabase.createdObjects);
        playerMoveCoordinator = new PlayerMoveCoordinator(player, obstacles);
    }

    @Override
    public void render() {
        levelRenderer.clear();

        float deltaTime = levelRenderer.getDeltaTime();

        PlayerMove playerMove = UserInput.handleUserInput();
        if (playerMove != null) {
            playerMoveCoordinator.makeMove(playerMove);
        }
        playerMoveCoordinator.confirmMove(deltaTime);
        levelRenderer.shiftEntity(
                player.getPlayerObject(), playerMoveCoordinator.getDestination(), playerMoveCoordinator.getMovementProgress()
        );

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
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        levelRenderer.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
