package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher extends GameLauncher {
    private LevelTiles levelTiles;

    private Player player;

    private MapRendering mapRendering;

    @Override
    public void create() {
        levelTiles = new LevelTiles(GameConfig.mapFileName);
        player = new Player(GameConfig.textureFileName);
        mapRendering = new MapRendering(GameConfig.objectTextureFileName);

        moveRectangleAtTileCenter(levelTiles.getGroundLayer(), mapRendering.getObjectObstacleRectangle(), mapRendering.getObjectObstacleCoordinates());
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            player.moveUp(mapRendering);
        }

        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            player.moveLeft(mapRendering);
        }

        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            player.moveDown(mapRendering);
        }

        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            player.moveRight(mapRendering);
        }

        // calculate interpolated player screen coordinates
        levelTiles.getTileMovement().moveRectangleBetweenTileCenters(player);

        player.applyMovementPerDeltaTime(deltaTime);

        // render each tile of the level
        levelTiles.getLevelRenderer().render();

        // start recording all drawing commands
        levelTiles.getBatch().begin();

        // render player
        drawTextureRegionUnscaled(levelTiles.getBatch(), player.getGraphics(), player.getRectangle(), player.getRotation());

        // render tree obstacle
        drawTextureRegionUnscaled(levelTiles.getBatch(), mapRendering.getObjectObstacleGraphics(), mapRendering.getObjectObstacleRectangle(), 0f);

        // submit all drawing requests
        levelTiles.getBatch().end();
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
        mapRendering.getObjectTexture().dispose();
        player.getTexture().dispose();
        levelTiles.getLevel().dispose();
        levelTiles.getBatch().dispose();
    }
}
