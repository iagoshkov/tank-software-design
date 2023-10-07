package ru.mipt.bit.platformer.entities;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.instructions.Direction;

import static org.junit.jupiter.api.Assertions.*;
import static ru.mipt.bit.platformer.graphics.CollisionDetector.collides;
import static ru.mipt.bit.platformer.util.GdxGameUtils.decrementedY;

class TankTest {

    @Test
    public void TestTankGetFields() {
        GridPoint2 coordinates = new GridPoint2(1, 1);
        Direction direction = Direction.DOWN;

        Tank tank = new Tank(coordinates, direction, 1);

        assertEquals(coordinates, tank.getCoordinates());
        assertEquals(tank.getCoordinates(), tank.getDestinationCoordinates());
        assertEquals(1, tank.getMovementProgress());
        assertEquals(direction.getRotation(), tank.getRotation());
    }

    @Test
    public void TestTankCollides() {
        GridPoint2 objectCoordinates = new GridPoint2(0, 1);
        MapObject object = new Tree(objectCoordinates);

        GridPoint2 coordinates = new GridPoint2(1, 1);
        Direction direction = Direction.DOWN;

        Tank tank = new Tank(coordinates, direction, 1);

        assertFalse(collides(tank, coordinates));
        assertTrue(collides(tank, objectCoordinates));
    }

    @Test
    public void TestTankMoveIfNoCollides() {
        GridPoint2 objectCoordinates = new GridPoint2(0, 3);
        MapObject object = new Tree(objectCoordinates);

        GridPoint2 coordinates = new GridPoint2(0, 1);
        Direction direction = Direction.DOWN;

        Tank tank = new Tank(coordinates, direction, 1);

        tank.move(Direction.UP);
        tank.updateState(1);

        tank.move(Direction.UP);
        tank.updateState(1);

        assertEquals(decrementedY(objectCoordinates), tank.getCoordinates());
        assertEquals(Direction.UP.getRotation(), tank.getRotation());
    }
}