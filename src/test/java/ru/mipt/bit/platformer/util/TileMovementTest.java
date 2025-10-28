package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileMovementTest {

    private static final int TILE_SIZE = 128;

    private static class DummyLayer extends TiledMapTileLayer {
        public DummyLayer(int width, int height) {
            super(width, height, TILE_SIZE, TILE_SIZE);
        }
    }

    @Test
    void testMoveBetweenTileCenters() {
        TiledMapTileLayer layer = new DummyLayer(10, 10);
        TileMovement movement = new TileMovement(layer, Interpolation.linear);

        Rectangle rect = new Rectangle();
        GridPoint2 from = new GridPoint2(0, 0);
        GridPoint2 to = new GridPoint2(1, 0);

        movement.moveRectangleBetweenTileCenters(rect, from, to, 0f);
        float centerX = rect.x + TILE_SIZE / 2f;
        float centerY = rect.y + TILE_SIZE / 2f;

        assertEquals(TILE_SIZE / 2f, centerX, 1e-3);
        assertEquals(TILE_SIZE / 2f, centerY, 1e-3);

        movement.moveRectangleBetweenTileCenters(rect, from, to, 1f);
        centerX = rect.x + TILE_SIZE / 2f;
        centerY = rect.y + TILE_SIZE / 2f;

        assertEquals(1.5f * TILE_SIZE, centerX, 1e-3);
        assertEquals(TILE_SIZE / 2f, centerY, 1e-3);
    }
}
