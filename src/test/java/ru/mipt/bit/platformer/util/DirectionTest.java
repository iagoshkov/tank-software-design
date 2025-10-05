package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import static org.junit.Assert.*;

public class DirectionTest {

    @Test
    public void testUpCalculateNewPosition() {
        GridPoint2 current = new GridPoint2(1, 1);
        GridPoint2 newPos = Direction.UP.calculateNewPosition(current);
        assertEquals(new GridPoint2(1, 2), newPos);
    }

    @Test
    public void testDownCalculateNewPosition() {
        GridPoint2 current = new GridPoint2(1, 1);
        GridPoint2 newPos = Direction.DOWN.calculateNewPosition(current);
        assertEquals(new GridPoint2(1, 0), newPos);
    }

    @Test
    public void testLeftCalculateNewPosition() {
        GridPoint2 current = new GridPoint2(1, 1);
        GridPoint2 newPos = Direction.LEFT.calculateNewPosition(current);
        assertEquals(new GridPoint2(0, 1), newPos);
    }

    @Test
    public void testRightCalculateNewPosition() {
        GridPoint2 current = new GridPoint2(1, 1);
        GridPoint2 newPos = Direction.RIGHT.calculateNewPosition(current);
        assertEquals(new GridPoint2(2, 1), newPos);
    }

    @Test
    public void testGetRotation() {
        assertEquals(90f, Direction.UP.getRotation(), 0.01f);
        assertEquals(-90f, Direction.DOWN.getRotation(), 0.01f);
        assertEquals(-180f, Direction.LEFT.getRotation(), 0.01f);
        assertEquals(0f, Direction.RIGHT.getRotation(), 0.01f);
    }
}