package ru.mipt.bit.platformer.models;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.util.TileMovement;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {

    @Test
    void treeIsPositionedAtTileCenter() {
        TiledMapTileLayer layer = new TiledMapTileLayer(10, 10, 128, 128);
        TileMovement movement = new TileMovement(layer, Interpolation.linear);
        Tree tree = new Tree(movement, new GridPoint2(3, 2));

        Rectangle rect = tree.getRectangle();
        assertEquals(3 * 128f, rect.x, 1e-3);
        assertEquals(2 * 128f, rect.y, 1e-3);
    }
}
