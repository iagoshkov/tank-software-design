package ru.mipt.bit.platformer.tests;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Direction;

public class DirectionTest {
    
    public void testGetNextPosition() {
        GridPoint2 start = new GridPoint2(2, 2);
        assert Direction.RIGHT.getNextPosition(start).equals(new GridPoint2(3, 2));
        assert Direction.LEFT.getNextPosition(start).equals(new GridPoint2(1, 2));
        assert Direction.UP.getNextPosition(start).equals(new GridPoint2(2, 3));
        assert Direction.DOWN.getNextPosition(start).equals(new GridPoint2(2, 1));
        assert Direction.NULL.getNextPosition(start).equals(new GridPoint2(2, 2));
    }

    public void testGetRotation() {
        assert Direction.RIGHT.getRotation() == 0f;
        assert Direction.LEFT.getRotation() == 180f;
        assert Direction.UP.getRotation() == 90f;
        assert Direction.DOWN.getRotation() == -90f;
        assert Direction.NULL.getRotation() == 0f;
    }
}