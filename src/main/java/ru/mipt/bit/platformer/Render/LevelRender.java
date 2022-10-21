package ru.mipt.bit.platformer.Render;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.Entities.TextureObstacle;
import ru.mipt.bit.platformer.Obstacles.IObstacleInLevel;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.HashMap;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getLayerByName;

public class LevelRender {
    private static LevelRender instance;
    public TiledMap tiledMap;
    public MapRenderer levelRenderer;
    public HashMap<String, TileMovement> mapMovements;

    private LevelRender() {
        this.mapMovements = new HashMap<>();
    }

    public static LevelRender getInstance(){
        if(instance == null){
            instance = new LevelRender();
        }
        return instance;
    }

    public void setLet(IObstacleInLevel obstacleInLevel){
        TiledMapTileLayer groundLayer = getSingleLayer(this.tiledMap);

        for (TextureObstacle entity : obstacleInLevel.getObstacleEntities()) {
            moveRectangleAtTileCenter(groundLayer, entity.obstacleVisualisation.rectangle, entity.position);
        }
    }

    public void setLevelMap(String pathToScheme){
        this.tiledMap = new TmxMapLoader().load(pathToScheme);
    }

    public void setLayerRenderer(Batch batch){
        this.levelRenderer = createSingleLayerMapRenderer(this.tiledMap, batch);
    }

    public void setLayerMovement(String nameLayer, Interpolation interpolation){
        TiledMapTileLayer layer = getLayerByName(this.tiledMap, nameLayer);
        TileMovement movement = new TileMovement(layer, interpolation);
        this.mapMovements.put(nameLayer, movement);
    }

    public void render(){
        this.levelRenderer.render();
    }
}
