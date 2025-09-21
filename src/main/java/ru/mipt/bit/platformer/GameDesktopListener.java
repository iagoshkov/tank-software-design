package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.service.LevelService;

public class GameDesktopListener implements ApplicationListener {
    private Batch batch;
    private LevelService levelService;

    @Override
    public void create() {
        batch = new SpriteBatch();
        levelService = new LevelService(new GridPoint2(1, 3), new GridPoint2(1, 1), "level.tmx", "images/tank_blue.png", "images/greenTree.png", batch);
    }

    @Override
    public void render() {
        levelService.processLevel();
        batch.begin();
        levelService.drawTexture(batch);
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

    @Override
    public void dispose() {
        levelService.dispose();
        batch.dispose();
    }
}
