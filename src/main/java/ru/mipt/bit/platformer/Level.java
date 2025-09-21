package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Interpolation;

import ru.mipt.bit.platformer.objects.GameObject;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class Level {
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final TileMovement tileMovement;
    private final TiledMapTileLayer groundLayer;

    public Level() {
        map = new TmxMapLoader().load("level.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        groundLayer = (TiledMapTileLayer) map.getLayers().get(0);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
    }

    public void placeObject(GameObject object) {
        moveRectangleAtTileCenter(groundLayer, object.getBounds(), object.getCoordinates());
    }

    public void render(Batch batch) {
        mapRenderer.setView(batch.getProjectionMatrix(), 0, 0, 1280, 1024);
        mapRenderer.render();
    }

    public void dispose() {
        map.dispose();
        mapRenderer.dispose();
    }

    public TileMovement getTileMovement() {
        return tileMovement;
    }

    public TiledMapTileLayer getGroundLayer() {
        return groundLayer;
    }
}