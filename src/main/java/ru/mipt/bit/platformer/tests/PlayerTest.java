package ru.mipt.bit.platformer.tests;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Level;
import ru.mipt.bit.platformer.configs.PlayerConfig;
import ru.mipt.bit.platformer.objects.Player;
import ru.mipt.bit.platformer.util.Direction;

public class PlayerTest {
    
    public void testPlayerMoveToNewDirection() {
        Level level = new Level();
        Player player = new Player(new PlayerConfig(new GridPoint2(1, 0)), level);
        player.move(Direction.UP);
        
        assert player.getDestinationCoordinates().equals(new GridPoint2(1, 1));
        assert player.getRotation() == Direction.UP.getRotation();
    }

    public void testPlayerIsMoving() {
        Level level = new Level();
        Player player = new Player(new PlayerConfig(new GridPoint2(1, 0)), level);
        
        assert !player.isMoving();
        player.move(Direction.LEFT);
        assert player.isMoving();
    }
}