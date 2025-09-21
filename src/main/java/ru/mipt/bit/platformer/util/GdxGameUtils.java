package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.NoSuchElementException;

public final class GdxGameUtils {

    public static MapRenderer createSingleLayerMapRenderer(TiledMap tiledMap, Batch batch) {
        TiledMapTileLayer tileLayer = getSingleLayer(tiledMap);
        float viewWidth = tileLayer.getWidth() * tileLayer.getTileWidth();
        float viewHeight = tileLayer.getHeight() * tileLayer.getTileHeight();

        OrthogonalTiledMapRenderer mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, batch);
        mapRenderer.getViewBounds().set(0f, 0f, viewWidth, viewHeight);

        return mapRenderer;
    }

    public static <L extends MapLayer> L getSingleLayer(Map map) {
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

}
