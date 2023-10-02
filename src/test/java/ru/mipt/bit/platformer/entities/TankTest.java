package ru.mipt.bit.platformer.entities;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.instructions.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

        assertFalse(tank.collides(coordinates, Collections.singletonList(object)));
        assertTrue(tank.collides(objectCoordinates, Collections.singletonList(object)));
    }

    @Test
    public void TestTankMoveIfNoCollides() {
        GridPoint2 objectCoordinates = new GridPoint2(0, 3);
        MapObject object = new Tree(objectCoordinates);

        GridPoint2 coordinates = new GridPoint2(0, 1);
        Direction direction = Direction.DOWN;

        Tank tank = new Tank(coordinates, direction, 1);

        tank.moveIfNotCollides(Direction.UP, Collections.singletonList(object));
        tank.updateState(1);

        tank.moveIfNotCollides(Direction.UP, Collections.singletonList(object));
        tank.updateState(1);

        assertEquals(decrementedY(objectCoordinates), tank.getCoordinates());
        assertEquals(Direction.UP.getRotation(), tank.getRotation());
    }
}