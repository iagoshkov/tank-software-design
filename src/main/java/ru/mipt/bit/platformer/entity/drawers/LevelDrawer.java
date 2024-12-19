package ru.mipt.bit.platformer.entity.drawers;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;

import ru.mipt.bit.platformer.entity.tree.Tree;
import ru.mipt.bit.platformer.entity.tank.Tank;
import ru.mipt.bit.platformer.entity.level.Level;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.List;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class LevelDrawer implements GraphicObject {

    private Batch batch;
    private String filePath;
    private TiledMap map;
    private MapRenderer renderer;
    private TiledMapTileLayer groundLayer;
    private TileMovement tileMovement;
    private Level level;
    private LevelObjectDrawer tankDrawer;
    private final List<LevelObjectDrawer> treesDrawer = new ArrayList<LevelObjectDrawer>();


    public LevelDrawer(String filePath, Batch batch, Level level) {
        this.filePath = filePath;
        this.batch = batch;
        this.level = level;
    }

    public void draw() {
        drawLevel();
        drawTank();
        drawTrees();
    }

    public void drawLevel() {
        this.map = new TmxMapLoader().load(this.filePath);
        this.renderer = createSingleLayerMapRenderer(this.map, this.batch);
        this.groundLayer = getSingleLayer(this.map);
        this.tileMovement = new TileMovement(this.groundLayer, Interpolation.smooth);
    }

    public void drawTank() {
        tankDrawer = new LevelObjectDrawer("images/tank_blue.png");
        tankDrawer.draw();
    }

    public void drawTrees() {
        for (Tree tree : level.getTress()) {
            LevelObjectDrawer treeDrawerObject = new LevelObjectDrawer("images/greenTree.png");
            treesDrawer.add(treeDrawerObject);
            treeDrawerObject.draw();
            moveRectangleAtTileCenter(groundLayer, treeDrawerObject.getRectangle(), tree.getCoordinates());
        }
    }

    public void renderMoves(float deltaTime) {
        Tank tank = level.getTank();
        tileMovement.moveRectangleBetweenTileCenters(tankDrawer.getRectangle(), tank.getCoordinates(), tank.getDestinationCoordinates(), tank.getMovementProgress());
        tank.updateState(deltaTime);

        renderer.render();
    }

    public void renderObjects() {
        Tank tank = level.getTank();
        drawTextureRegionUnscaled(batch, tankDrawer.getGraphics(), tankDrawer.getRectangle(), tank.getRotation());
        for (LevelObjectDrawer treeDrawer : treesDrawer) {
            drawTextureRegionUnscaled(batch, treeDrawer.getGraphics(), treeDrawer.getRectangle(), 0f);
        }
    }

    public void recordDrawCommand() {
        // start recording all drawing commands
        batch.begin();
        renderObjects();
        // submit all drawing requests
        batch.end();
    }

    public void dispose() {
        tankDrawer.dispose();
        for (LevelObjectDrawer treeDrawer : treesDrawer) {
            treeDrawer.dispose();
        }
        map.dispose();
        batch.dispose();
    }
}
