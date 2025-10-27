package ru.tests;
//home/howard/tank/hw/tank-software-design/src/java/ru/mipt/bit/platformer/test
import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import static org.junit.Assert.*;

public class DirectionTest {

    @Test
    public void testDirectionVectors() {
        assertEquals(new GridPoint2(1, 0), Direction.RIGHT.getDirectionVector());
        assertEquals(new GridPoint2(-1, 0), Direction.LEFT.getDirectionVector());
        assertEquals(new GridPoint2(0, 1), Direction.UP.getDirectionVector());
        assertEquals(new GridPoint2(0, -1), Direction.DOWN.getDirectionVector());
    }

    @Test
    public void testNextPosition() {
        GridPoint2 start = new GridPoint2(2, 2);
        assertEquals(new GridPoint2(3, 2), Direction.RIGHT.getNextPosition(start));
        assertEquals(new GridPoint2(1, 2), Direction.LEFT.getNextPosition(start));
        assertEquals(new GridPoint2(2, 3), Direction.UP.getNextPosition(start));
        assertEquals(new GridPoint2(2, 1), Direction.DOWN.getNextPosition(start));
    }

    @Test
    public void testRotations() {
        assertEquals(0f, Direction.RIGHT.getRotation(), 0.01f);
        assertEquals(180f, Direction.LEFT.getRotation(), 0.01f);
        assertEquals(90f, Direction.UP.getRotation(), 0.01f);
        assertEquals(-90f, Direction.DOWN.getRotation(), 0.01f);
    }
}