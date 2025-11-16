package ru.mipt.bit.platformer.tests;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Level;
import ru.mipt.bit.platformer.configs.PlayerConfig;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.util.Direction;

public class TankTest {
    
    public void testTankMoveToNewDirection() {
        Level level = new Level();
        Tank tank = new Tank(new PlayerConfig(new GridPoint2(1, 0)), level, false);
        tank.move(Direction.RIGHT);
        
        assert tank.getDestinationCoordinates().equals(new GridPoint2(2, 0));
        assert tank.getRotation() == Direction.RIGHT.getRotation();
    }

    public void testTankIsMoving() {
        Level level = new Level();
        Tank tank = new Tank(new PlayerConfig(new GridPoint2(1, 0)), level, false);
        
        assert !tank.isMoving();
        tank.move(Direction.RIGHT);
        assert tank.isMoving();
    }
}