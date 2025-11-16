package ru.mipt.bit.platformer.tests;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Level;


public class LevelTest {
    
    public void testIsPositionValid() {
        Level level = new Level();
        assert level.isPositionValid(new GridPoint2(0, 0));
        assert level.isPositionValid(new GridPoint2(5, 5));
        assert !level.isPositionValid(new GridPoint2(-1, 0));
        assert !level.isPositionValid(new GridPoint2(100, 100));
    }

    public void testPositionOccupancy() {
        Level level = new Level();
        GridPoint2 position = new GridPoint2(2, 2);
        assert !level.isPositionOccupied(position);
    }
}