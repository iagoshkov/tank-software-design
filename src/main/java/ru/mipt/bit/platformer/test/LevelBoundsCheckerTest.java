package ru.mipt.bit.platformer.test;

import org.junit.Before;
import org.junit.Test;
import ru.mipt.bit.platformer.collision.LevelBoundsChecker;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LevelBoundsCheckerTest {
    
    private LevelBoundsChecker boundsChecker;
    private TiledMapTileLayer mockLayer;
    
    @Before
    public void setUp() {
        mockLayer = mock(TiledMapTileLayer.class);
        when(mockLayer.getWidth()).thenReturn(10);
        when(mockLayer.getHeight()).thenReturn(8);
        boundsChecker = new LevelBoundsChecker(mockLayer);
    }
    
    @Test
    public void testWithinBounds() {
        assertTrue(boundsChecker.isWithinBounds(new GridPoint2(5, 5)));
        assertTrue(boundsChecker.isWithinBounds(new GridPoint2(0, 0)));
        assertTrue(boundsChecker.isWithinBounds(new GridPoint2(9, 7)));
    }
    
    @Test
    public void testOutsideBounds() {
        assertFalse(boundsChecker.isWithinBounds(new GridPoint2(-1, 5)));
        assertFalse(boundsChecker.isWithinBounds(new GridPoint2(5, -1)));
        assertFalse(boundsChecker.isWithinBounds(new GridPoint2(10, 5)));
        assertFalse(boundsChecker.isWithinBounds(new GridPoint2(5, 8)));
    }
    
    @Test
    public void testLevelDimensions() {
        assertEquals(10, boundsChecker.getLevelWidth());
        assertEquals(8, boundsChecker.getLevelHeight());
    }
}