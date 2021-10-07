package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player player;
    private static final List<Obstacle> OBSTACLES = List.of(new Obstacle(new GridPoint2(6, 6)));

    @BeforeEach
    private void setUp() {
        player = new Player(new GridPoint2(5, 6), 90f);
    }

    @Test
    public void testValidMovement() {
        List<GridPoint2> path = List.of(
                new GridPoint2(5, 7),
                new GridPoint2(4, 7),
                new GridPoint2(4, 6),
                new GridPoint2(5, 6));
        List<Direction> directions = List.of(Direction.UP, Direction.LEFT, Direction.DOWN, Direction.RIGHT);
        for (int i = 0; i < path.size(); ++i) {
            var destination = path.get(i);
            var direction = directions.get(i);
            player.tryRotateAndStartMovement(direction, OBSTACLES);
            player.makeProgress(1f);
            player.tryFinishMovement();
            assertEquals(player.getCoordinates(), path.get(i));
        }
    }

    @Test
    public void testCollision() {
        player.tryRotateAndStartMovement(Direction.RIGHT, OBSTACLES);
        assertFalse(player.isMoving());
        assertEquals(0f, player.getRotation());
    }
}
