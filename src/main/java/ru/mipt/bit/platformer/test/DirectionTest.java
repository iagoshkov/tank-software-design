package ru.mipt.bit.platformer.test;

import org.junit.Test;
import ru.mipt.bit.platformer.model.Direction;
import com.badlogic.gdx.math.GridPoint2;

import static org.junit.Assert.*;

public class DirectionTest {
    
    @Test
    public void testDirectionApplication() {
        GridPoint2 start = new GridPoint2(2, 2);
        
        assertEquals(new GridPoint2(2, 3), Direction.UP.apply(start));
        assertEquals(new GridPoint2(2, 1), Direction.DOWN.apply(start));
        assertEquals(new GridPoint2(1, 2), Direction.LEFT.apply(start));
        assertEquals(new GridPoint2(3, 2), Direction.RIGHT.apply(start));
    }
    
    @Test
    public void testRotationValues() {
        assertEquals(90f, Direction.UP.getRotation(), 0.001f);
        assertEquals(-90f, Direction.DOWN.getRotation(), 0.001f);
        assertEquals(-180f, Direction.LEFT.getRotation(), 0.001f);
        assertEquals(0f, Direction.RIGHT.getRotation(), 0.001f);
    }
    
    @Test
    public void testDirectionVectors() {
        assertEquals(new GridPoint2(0, 1), Direction.UP.getDirectionVector());
        assertEquals(new GridPoint2(0, -1), Direction.DOWN.getDirectionVector());
        assertEquals(new GridPoint2(-1, 0), Direction.LEFT.getDirectionVector());
        assertEquals(new GridPoint2(1, 0), Direction.RIGHT.getDirectionVector());
    }
    
    @Test
    public void testKeyCodes() {
        assertTrue(Direction.UP.getKeyCodes().length > 0);
        assertTrue(Direction.DOWN.getKeyCodes().length > 0);
        assertTrue(Direction.LEFT.getKeyCodes().length > 0);
        assertTrue(Direction.RIGHT.getKeyCodes().length > 0);
    }
}