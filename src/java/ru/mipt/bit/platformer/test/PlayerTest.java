package ru.tests;


import com.badlogic.gdx.math.GridPoint2;
import org.junit.Before;
import org.junit.Test;
import ru.mipt.bit.platformer.Level;
import ru.mipt.bit.platformer.util.Direction;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PlayerTest {

    private Player player;
    private Level level;
    private GridPoint2 startPosition;

    @Before
    public void setUp() {
        startPosition = new GridPoint2(1, 1);
        level = mock(Level.class);
        player = new Player(startPosition, level);
    }

    @Test
    public void testInitialState() {
        assertEquals(startPosition, player.getCoordinates());
        assertFalse(player.isMoving());
        assertEquals(0f, player.getRotation(), 0.01f);
    }

    @Test
    public void testMove() {
        player.move(Direction.RIGHT);
        
        assertTrue(player.isMoving());
        assertEquals(Direction.RIGHT.getRotation(), player.getRotation(), 0.01f);
    }

    @Test
    public void testCannotMoveWhileMoving() {
        player.move(Direction.RIGHT);
        assertTrue(player.isMoving());
        
        // Try to move again
        GridPoint2 originalDestination = new GridPoint2(player.getCoordinates());
        player.move(Direction.UP);
        
        // Should still be moving to original destination
        assertEquals(originalDestination, new GridPoint2(player.getCoordinates().x + 1, player.getCoordinates().y));
    }

    @Test
    public void testCanMoveTo() {
        Tree tree = new Tree(new GridPoint2(2, 2), level);
        
        assertTrue(player.canMoveTo(new GridPoint2(1, 2), tree));
        assertFalse(player.canMoveTo(new GridPoint2(2, 2), tree));
    }

    @Test
    public void testUpdateMovement() {
        player.move(Direction.RIGHT);
        float deltaTime = 0.1f;
        
        player.update(deltaTime);
        
        // Player should still be moving
        assertTrue(player.isMoving());
    }
}