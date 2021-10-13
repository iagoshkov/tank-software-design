package ru.mipt.bit.platformer.driver;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.DirectionResolver;
import ru.mipt.bit.platformer.UserInput;
import ru.mipt.bit.platformer.graphic.GameGraphics;
import ru.mipt.bit.platformer.graphic.GraphicsSimpleObject;
import ru.mipt.bit.platformer.logic.GameLogic;
import ru.mipt.bit.platformer.logic.Point2D;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class GameDriver implements ApplicationListener {

    private final DirectionResolver directionResolver;
    private final GameLogicInitializer gameLogicInitializer;
    private GameLogic gameLogic;
    private GameGraphics gameGraphics;

    public GameDriver(
            final DirectionResolver directionResolver,
            final GameLogicInitializer gameLogicInitializer) {
        this.directionResolver = directionResolver;
        this.gameLogicInitializer = gameLogicInitializer;
    }

    @Override
    public void create() {
        final var level = new TmxMapLoader().load("level.tmx");
        gameLogic = gameLogicInitializer.init(toMap(level.getProperties()));
        final var batch = new SpriteBatch();
        final var tileMovement = new TileMovement(getSingleLayer(level), Interpolation.smooth);
        final var playerTexture = new Texture("images/tank_blue.png");
        final var treeTexture = new Texture("images/greenTree.png");
        final var player = gameLogic.getPlayer();
        final var obstaclePoint2D = gameLogic.getObstacles().get(0);
        gameGraphics = new GameGraphics(
                batch,
                level,
                new GraphicsSimpleObject(
                        tileMovement,
                        toGdxGridPoint(player.getCurrentPosition()),
                        toGdxGridPoint(player.getDestinationPosition()),
                        playerTexture,
                        new TextureRegion(playerTexture),
                        player.getRotation(),
                        player.getMoveProgress()
                ),
                new GraphicsSimpleObject(
                        tileMovement,
                        toGdxGridPoint(obstaclePoint2D),
                        toGdxGridPoint(obstaclePoint2D),
                        treeTexture,
                        new TextureRegion(treeTexture),
                        0,
                        1
                )
        );
    }

    @Override
    public void resize(final int width, final int height) {
    }

    @Override
    public void render() {
        float timePassedSinceLastRender = getTimePassedSinceLastRender();
        clearScreen();
        gameLogic.update(
                new UserInput(directionResolver.resolveDirection().orElse(null)),
                timePassedSinceLastRender
        );
        final var player = gameLogic.getPlayer();
        gameGraphics.getPlayerGraphics()
                .withCurrent(toGdxGridPoint(player.getCurrentPosition()))
                .withDestination(toGdxGridPoint(player.getDestinationPosition()))
                .withRotation(player.getRotation())
                .withMoveProgress(player.getMoveProgress());
        gameGraphics.render();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        if (gameGraphics != null) {
            gameGraphics.close();
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private float getTimePassedSinceLastRender() {
        return Gdx.graphics.getDeltaTime();
    }

    private static GridPoint2 toGdxGridPoint(final Point2D point2D) {
        return new GridPoint2(point2D.getX(), point2D.getY());
    }

    private static Map<String, Object> toMap(final MapProperties props) {
        final Iterable<String> keyIterable = props::getKeys;
        final var result = new HashMap<String, Object>();
        for (var key : keyIterable) {
            result.put(key, props.get(key));
        }
        return result;
    }
}
