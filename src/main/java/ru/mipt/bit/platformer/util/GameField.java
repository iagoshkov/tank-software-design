package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;

public class GameField implements Field {
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

    @Override
    public boolean isPositionOccupied(GridPoint2 position) {
        for (GameEntity gameEntity : gameEntities) {
            if (gameEntity.getModel() instanceof PositionOccupier) {
                PositionOccupier occupier = (PositionOccupier) gameEntity.getModel();
                if (occupier.occupiesPosition(position)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void initializeLevel(LevelGenerator.LevelData levelData, Texture tankTexture, Texture treeTexture, TileMovement tileMovement) {
        // Очищаем существующие сущности
        gameEntities.clear();

        // Создаем танк
        TankModel tankModel = new TankModel(levelData.getTankPosition(), 0.4f);
        TankView tankView = new TankView(tankModel, new TextureRegion(tankTexture), groundLayer, tileMovement);
        GameEntity tankEntity = new GameEntity(tankModel, tankView);
        addGameEntity(tankEntity);

        // Создаем деревья
        for (GridPoint2 treePos : levelData.getTreePositions()) {
            TreeModel treeModel = new TreeModel(treePos);
            TreeView treeView = new TreeView(treeModel, new TextureRegion(treeTexture), groundLayer);
            GameEntity treeEntity = new GameEntity(treeModel, treeView);
            addGameEntity(treeEntity);
        }
    }
}