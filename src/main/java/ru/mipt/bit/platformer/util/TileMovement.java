package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import ru.mipt.bit.platformer.models.graphics.Tank;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class TileMovement {

    private final TiledMapTileLayer tileLayer;
    private final Interpolation interpolation;

    public TileMovement(TiledMapTileLayer tileLayer, Interpolation interpolation) {
        this.tileLayer = tileLayer;
        this.interpolation = interpolation;
    }

    public void calculateMovableGameObjectCoordinates(Tank movingGraphicObject) {
        Rectangle rectangle = movingGraphicObject.getRectangle();
        float progress = movingGraphicObject.getMovementProgress();

        GridPoint2 fromTileCoordinates = movingGraphicObject.getCoordinates();
        GridPoint2 toTileCoordinates = movingGraphicObject.getDestinationCoordinates();

        Vector2 from = moveRectangleAtTileCenter(tileLayer, rectangle, fromTileCoordinates);
        Vector2 to = moveRectangleAtTileCenter(tileLayer, rectangle, toTileCoordinates);

        float intermediateBottomLeftX = interpolation.apply(from.x, to.x, progress);
        float intermediateBottomLeftY = interpolation.apply(from.y, to.y, progress);

        rectangle
                .setX(intermediateBottomLeftX)
                .setY(intermediateBottomLeftY);
    }
}
