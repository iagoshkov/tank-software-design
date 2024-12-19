package ru.tests;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ru.mipt.bit.platformer.entity.tank.Tank;
import ru.mipt.bit.platformer.entity.tank.Direction;

public class TankTest {
    @ParameterizedTest
    @EnumSource(Direction.class)
    public void testTankMoveToNewDirectionWithCoordinates(Direction direction){
        GridPoint2 directionPoint = direction.getDirectionPoint();

        Tank tank = new Tank(new GridPoint2(1, 0));
        tank.move(directionPoint, direction.getDirectionRotation());

        GridPoint2 targetCoordinates = tank.getCoordinates();
        targetCoordinates.add(directionPoint.x, directionPoint.y);

        assertEquals(targetCoordinates, tank.getDestinationCoordinates());
        assertEquals(direction.getDirectionRotation(), tank.getRotation());
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    void testTankFinishedMovement(Direction direction) {
        GridPoint2 directionPoint = direction.getDirectionPoint();
        float directionRotation = direction.getDirectionRotation();

        Tank tank = new Tank(new GridPoint2(1, 0));
        float deltaTime = 1f;

        GridPoint2 targetCoordinates = tank.getCoordinates();
        targetCoordinates.add(directionPoint.x, directionPoint.y);

        tank.move(directionPoint, directionRotation);
        tank.updateState(deltaTime);

        assertEquals(targetCoordinates, tank.getCoordinates());
        assertEquals(targetCoordinates, tank.getDestinationCoordinates());
        assertEquals(directionRotation, tank.getRotation());

    }
}
