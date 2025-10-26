package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import static org.junit.Assert.*;

public class TankModelTest {

    @Test
    public void testInitialState() {
        GridPoint2 initialPos = new GridPoint2(1, 1);
        TankModel tank = new TankModel(initialPos, 0.4f);
        assertEquals(initialPos, tank.getCoordinates());
        assertTrue(tank.isMovementComplete());
        assertEquals(0f, tank.getRotation(), 0.01f);
    }

    @Test
    public void testMoveTo() {
        TankModel tank = new TankModel(new GridPoint2(1, 1), 0.4f);
        GridPoint2 newPos = new GridPoint2(1, 2);
        tank.moveTo(newPos, Direction.UP);
        assertEquals(90f, tank.getRotation(), 0.01f);
        assertEquals(new GridPoint2(1, 1), tank.getCoordinates()); // not yet moved
        assertEquals(newPos, tank.getDestinationCoordinates());
        assertFalse(tank.isMovementComplete());
    }

    @Test
    public void testUpdateMovement() {
        TankModel tank = new TankModel(new GridPoint2(1, 1), 1f); // speed 1
        GridPoint2 newPos = new GridPoint2(1, 2);
        tank.moveTo(newPos, Direction.UP);
        tank.update(1f); // deltaTime 1 to complete movement
        assertTrue(tank.isMovementComplete());
        assertEquals(newPos, tank.getCoordinates());
    }
}