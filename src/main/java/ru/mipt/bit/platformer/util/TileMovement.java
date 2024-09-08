package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.Player;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class TileMovement {

    private final TiledMapTileLayer tileLayer;
    private final Interpolation interpolation;

    public TileMovement(TiledMapTileLayer tileLayer, Interpolation interpolation) {
        this.tileLayer = tileLayer;
        this.interpolation = interpolation;
    }

    public Rectangle moveRectangleBetweenTileCenters(Player player) {
        moveRectangleAtTileCenter(tileLayer, player.getRectangle(), player.getCoordinates());
        float fromTileBottomLeftX = player.getRectangle().x;
        float fromTileBottomLeftY = player.getRectangle().y;

        moveRectangleAtTileCenter(tileLayer, player.getRectangle(), player.getDestinationCoordinates());
        float toTileBottomLeftX = player.getRectangle().x;
        float toTileBottomLeftY = player.getRectangle().y;

        float intermediateBottomLeftX = interpolation.apply(fromTileBottomLeftX, toTileBottomLeftX, player.getMovementProgress());
        float intermediateBottomLeftY = interpolation.apply(fromTileBottomLeftY, toTileBottomLeftY, player.getMovementProgress());

        return player.getRectangle()
                .setX(intermediateBottomLeftX)
                .setY(intermediateBottomLeftY);
    }
}
