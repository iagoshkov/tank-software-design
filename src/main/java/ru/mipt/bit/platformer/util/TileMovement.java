package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class TileMovement {

    private final TiledMapTileLayer tileLayer;
    private final Interpolation interpolation;

    public TileMovement(TiledMapTileLayer tileLayer, Interpolation interpolation) {
        this.tileLayer = tileLayer;
        this.interpolation = interpolation;
    }

    public Rectangle moveRectangleBetweenTileCenters(Rectangle rectangle, GridPoint2 fromTileCoordinates, GridPoint2 toTileCoordinates, float progress) {
        float intermediateBottomLeftX = interpolation.apply(calculateRectangleCoordinates(rectangle, fromTileCoordinates).x,
                calculateRectangleCoordinates(rectangle, toTileCoordinates).x, progress);
        float intermediateBottomLeftY = interpolation.apply(calculateRectangleCoordinates(rectangle, fromTileCoordinates).y,
                calculateRectangleCoordinates(rectangle, toTileCoordinates).y, progress);

        return rectangle
                .setX(intermediateBottomLeftX)
                .setY(intermediateBottomLeftY);
    }
    private Vector2 calculateRectangleCoordinates(Rectangle rectangle, GridPoint2 tileCoordinates){
        moveRectangleAtTileCenter(tileLayer, rectangle, tileCoordinates);
        return new Vector2(rectangle.x, rectangle.y);
    }
}
