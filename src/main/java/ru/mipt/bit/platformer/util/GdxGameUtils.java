package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.NoSuchElementException;

public final class GdxGameUtils {
    private GdxGameUtils() {
    }

    public static MapRenderer createSingleLayerMapRenderer(TiledMap tiledMap, Batch batch) {
        TiledMapTileLayer tileLayer = (TiledMapTileLayer)getSingleLayer(tiledMap);
        float viewWidth = (float)(tileLayer.getWidth() * tileLayer.getTileWidth());
        float viewHeight = (float)(tileLayer.getHeight() * tileLayer.getTileHeight());
        OrthogonalTiledMapRenderer mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, batch);
        mapRenderer.getViewBounds().set(0.0F, 0.0F, viewWidth, viewHeight);
        return mapRenderer;
    }

    @SuppressWarnings("unchecked")
    public static <L extends MapLayer> L getSingleLayer(Map map) {
        MapLayers layers = map.getLayers();
        switch (layers.size()) {
            case 0:
                throw new NoSuchElementException("Map has no layers");
            case 1:
                MapLayer layer = layers.iterator().next();
                return (L) layer;
            default:
                throw new IllegalArgumentException("Map has more than one layer");
        }
    }

    public static Rectangle moveRectangleAtTileCenter(TiledMapTileLayer tileLayer, Rectangle rectangle, GridPoint2 tileCoordinates) {
        Vector2 tileCenter = calculateTileCenter(tileLayer, tileCoordinates);
        return rectangle.setCenter(tileCenter);
    }

    public static GridPoint2 incrementedY(GridPoint2 point) {
        return (new GridPoint2(point)).add(0, 1);
    }

    public static GridPoint2 decrementedX(GridPoint2 point) {
        return (new GridPoint2(point)).sub(1, 0);
    }

    public static GridPoint2 decrementedY(GridPoint2 point) {
        return (new GridPoint2(point)).sub(0, 1);
    }

    public static GridPoint2 incrementedX(GridPoint2 point) {
        return (new GridPoint2(point)).add(1, 0);
    }

    public static void drawTextureRegionUnscaled(Batch batch, TextureRegion region, Rectangle rectangle, float rotation) {
        int regionWidth = region.getRegionWidth();
        int regionHeight = region.getRegionHeight();
        float regionOriginX = (float)regionWidth / 2.0F;
        float regionOriginY = (float)regionHeight / 2.0F;
        batch.draw(region, rectangle.x, rectangle.y, regionOriginX, regionOriginY, (float)regionWidth, (float)regionHeight, 1.0F, 1.0F, rotation);
    }

    public static Rectangle createBoundingRectangle(TextureRegion region) {
        return (new Rectangle()).setWidth((float)region.getRegionWidth()).setHeight((float)region.getRegionHeight());
    }

    public static float continueProgress(float previousProgress, float deltaTime, float speed) {
        return MathUtils.clamp(previousProgress + deltaTime / speed, 0.0F, 1.0F);
    }

    private static Vector2 calculateTileCenter(TiledMapTileLayer tileLayer, GridPoint2 tileCoordinates) {
        int tileWidth = tileLayer.getTileWidth();
        int tileHeight = tileLayer.getTileHeight();
        int tileBottomLeftCornerX = tileCoordinates.x * tileWidth;
        int tileBottomLeftCornerY = tileCoordinates.y * tileHeight;
        return (new Rectangle()).setX((float)tileBottomLeftCornerX).setY((float)tileBottomLeftCornerY).setWidth((float)tileWidth).setHeight((float)tileHeight).getCenter(new Vector2());
    }
}