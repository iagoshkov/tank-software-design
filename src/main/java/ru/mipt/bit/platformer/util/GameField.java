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
    private final List<GameEntity> gameEntities;

    public GameField(MapRenderer levelRenderer, TiledMapTileLayer groundLayer) {
        this.levelRenderer = levelRenderer;
        this.groundLayer = groundLayer;
        this.gameEntities = new ArrayList<>();
    }

    public void addGameEntity(GameEntity gameEntity) {
        gameEntities.add(gameEntity);
    }

    public void renderLevel() {
        levelRenderer.render();
    }

    public void renderGameObjects(Batch batch) {
        for (GameEntity gameEntity : gameEntities) {
            gameEntity.render(batch);
        }
    }

    public void updateGameObjects(float deltaTime) {
        for (GameEntity gameEntity : gameEntities) {
            gameEntity.update(deltaTime);
        }
    }

    public TiledMapTileLayer getGroundLayer() {
        return groundLayer;
    }

    public List<GameEntity> getGameEntities() {
        return new ArrayList<>(gameEntities);
    }

    public boolean isPositionOccupied(GridPoint2 position) {
        for (GameEntity gameEntity : gameEntities) {
            if (gameEntity.getModel() instanceof TreeModel) {
                TreeModel tree = (TreeModel) gameEntity.getModel();
                if (tree.occupiesPosition(position)) {
                    return true;
                }
            }
        }
        return false;
    }
}