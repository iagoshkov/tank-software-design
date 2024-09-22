package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.NoSuchElementException;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;

public class LevelTiles {
    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;
    private Batch batch;
    private TiledMapTileLayer groundLayer;

    public LevelTiles(String mapFileName) {
        batch = new SpriteBatch();
        level = new TmxMapLoader().load(mapFileName);
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        groundLayer = LevelTiles.getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
    }

    public static <L extends MapLayer> L getSingleLayer(com.badlogic.gdx.maps.Map map) {
        MapLayers layers = map.getLayers();
        switch (layers.size()) {
            case 0:
                throw new NoSuchElementException("Map has no layers");
            case 1:
                @SuppressWarnings("unchecked")
                L layer = (L) layers.iterator().next();
                return layer;
            default:
                throw new IllegalArgumentException("Map has more than one layer");
        }
    }

    public TiledMap getLevel() {
        return level;
    }

    public MapRenderer getLevelRenderer() {
        return levelRenderer;
    }

    public TileMovement getTileMovement() {
        return tileMovement;
    }

    public TiledMapTileLayer getGroundLayer() {
        return groundLayer;
    }

    public Batch getBatch() {
        return batch;
    }
}
