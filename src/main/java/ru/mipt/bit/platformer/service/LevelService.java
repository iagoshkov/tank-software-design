package ru.mipt.bit.platformer.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.entity.Tank;
import ru.mipt.bit.platformer.entity.Tree;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class LevelService {
    private static final float MOVEMENT_SPEED = 0.4f;
    private final TiledMap levelMap;
    private final Tree tree;
    private final Tank tank;
    private final TextureRegion playerGraphics;
    private final Rectangle playerRectangle;
    private final MapRenderer levelRenderer;
    private final TileMovement tileMovement;
    private final TextureRegion treeObstacleGraphics;
    private final MovementService movementService;
    private Rectangle treeObstacleRectangle = new Rectangle();

    public LevelService(GridPoint2 treeCoordinates, GridPoint2 tankCoordinates, String pathToMap, String pathToTankTexture, String pathToTreeTexture, Batch batch) {
        tree = new Tree();
        tree.setTexture(new Texture(pathToTreeTexture));
        tree.setCoordinates(treeCoordinates);

        tank = new Tank();
        tank.setRotation(0f);
        tank.setMovementProgress(1f);
        tank.setCoordinates(tankCoordinates);
        tank.setCoordinatesDestination(tankCoordinates);
        tank.setTexture(new Texture(pathToTankTexture));

        playerGraphics = new TextureRegion(tank.getTexture());
        playerRectangle = createBoundingRectangle(playerGraphics);

        levelMap = new TmxMapLoader().load(pathToMap);
        levelRenderer = createSingleLayerMapRenderer(levelMap, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(levelMap);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        treeObstacleGraphics = new TextureRegion(tree.getTexture());
        treeObstacleRectangle = createBoundingRectangle(treeObstacleGraphics);
        moveRectangleAtTileCenter(groundLayer, treeObstacleRectangle, tree.getCoordinates());

        movementService = new MovementService();

    }

    public void processLevel() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        float deltaTime = Gdx.graphics.getDeltaTime();

        movementService.processMoving(tank, tree);

        tileMovement.moveRectangleBetweenTileCenters(playerRectangle, tank.getCoordinates(), tank.getCoordinatesDestination() , tank.getMovementProgress());

        tank.setMovementProgress(continueProgress(tank.getMovementProgress(), deltaTime, MOVEMENT_SPEED));
        if (isEqual(tank.getMovementProgress(), 1f)) {
            tank.setCoordinates(tank.getCoordinatesDestination());
        }

        levelRenderer.render();
    }

    public void drawTexture(Batch batch) {
        drawTextureRegionUnscaled(batch, playerGraphics, playerRectangle, tank.getRotation());
        drawTextureRegionUnscaled(batch, treeObstacleGraphics, treeObstacleRectangle, 0f);
    }

    public void dispose() {
        tree.getTexture().dispose();
        tank.getTexture().dispose();
        levelMap.dispose();
    }
}
