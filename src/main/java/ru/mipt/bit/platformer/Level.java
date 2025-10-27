package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.objects.GameObject;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.List;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class Level {
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final TileMovement tileMovement;
    private final TiledMapTileLayer groundLayer;
    private final List<GameObject> gameObjects;

    public Level() {
        map = new TmxMapLoader().load("src/main/resources/level.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        groundLayer = (TiledMapTileLayer) map.getLayers().get(0);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        gameObjects = new ArrayList<>();
    }

    public void placeObject(GameObject object) {
        gameObjects.add(object);
        moveRectangleAtTileCenter(groundLayer, object.getBounds(), object.getCoordinates());
    }

    // Добавьте этот метод
    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
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