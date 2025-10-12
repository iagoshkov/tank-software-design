package ru.tests;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TileMovementTest {

    private TileMovement tileMovement;
    private TiledMapTileLayer tileLayer;

    @Before
    public void setUp() {
        tileLayer = new TiledMapTileLayer(10, 10, 32, 32);
        tileMovement = new TileMovement(tileLayer, Interpolation.linear);
    }

    @Test
    public void testMoveRectangleBetweenTileCenters() {
        Rectangle rectangle = new Rectangle(0, 0, 32, 32);
        GridPoint2 from = new GridPoint2(0, 0);
        GridPoint2 to = new GridPoint2(1, 0);
        
        Rectangle result = tileMovement.moveRectangleBetweenTileCenters(rectangle, from, to, 0.5f);
        
        assertNotNull(result);
        // At 50% progress between (0,0) and (1,0), X should be halfway
        assertEquals(16f, result.x, 1.0f);
    }
}