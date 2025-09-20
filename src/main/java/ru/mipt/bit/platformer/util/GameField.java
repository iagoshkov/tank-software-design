package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;

public class GameField {
    private final MapRenderer levelRenderer;
    private final TiledMapTileLayer groundLayer;
    private final List<GameObject> gameObjects;

    public GameField(MapRenderer levelRenderer, TiledMapTileLayer groundLayer) {
        this.levelRenderer = levelRenderer;
        this.groundLayer = groundLayer;
        this.gameObjects = new ArrayList<>();
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void renderLevel() {
        levelRenderer.render();
    }

    public void renderGameObjects(Batch batch) {
        for (GameObject gameObject : gameObjects) {
            gameObject.render(batch);
        }
    }

    public void updateGameObjects(float deltaTime) {
        for (GameObject gameObject : gameObjects) {
            gameObject.update(deltaTime);
        }
    }

    public TiledMapTileLayer getGroundLayer() {
        return groundLayer;
    }

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public boolean isPositionOccupied(GridPoint2 position) {
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Tree) {
                Tree tree = (Tree) gameObject;
                if (tree.occupiesPosition(position)) {
                    return true;
                }
            }
        }
        return false;
    }
}