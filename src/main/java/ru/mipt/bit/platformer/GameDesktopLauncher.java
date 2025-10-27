package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.mipt.bit.platformer.controllers.PlayerInputController;
import ru.mipt.bit.platformer.objects.GameObject;
import ru.mipt.bit.platformer.objects.Player;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {
    private Batch batch;
    private Level level;
    private Player player;
    private InputHandler inputHandler;

    @Override
    public void create() {
        batch = new SpriteBatch();
        
        LevelGenerator generator = new FromFileLevelGenerator("src/assets/levels/level1.txt");
        level = generator.generate();
        player = generator.getPlayer();
        
        inputHandler = new InputHandler(player, new PlayerInputController());
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        
        float deltaTime = Gdx.graphics.getDeltaTime();
        
        inputHandler.handleInput();
        player.update(deltaTime);
        
        batch.begin();
        level.render(batch);
        
        // Рендерим все игровые объекты (игрока и деревья)
        for (GameObject gameObject : level.getGameObjects()) {
            gameObject.draw(batch);
        }
        
        batch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        player.dispose();
        level.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}