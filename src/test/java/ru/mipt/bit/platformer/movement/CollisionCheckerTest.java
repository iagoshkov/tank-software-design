package ru.mipt.bit.platformer.movement;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.objects.Obstacle;
import ru.mipt.bit.platformer.objects.Tank;

import static org.junit.jupiter.api.Assertions.*;

class CollisionCheckerTest {

    @Test
    void colliderTankMovementTest() {
        CollisionChecker colliderManager = new CollisionChecker();
        colliderManager.addColliding(new Obstacle(new GridPoint2(2, 3)));
        colliderManager.addColliding(new Obstacle(new GridPoint2(3, 2)));
        Tank tank = new Tank(new GridPoint2(3, 3), colliderManager);
        assertAll(
                () -> assertFalse(tank.collides(new GridPoint2(5, 5))),
                () -> assertFalse(tank.collides(new GridPoint2(2, 3))),
                () -> assertTrue(tank.collides(new GridPoint2(3, 3))),
                () -> assertFalse(tank.collides(new GridPoint2(3, 2)))
        );
        assertAll(
                () -> assertTrue(colliderManager.isFree(new GridPoint2(5, 5))),
                () -> assertFalse(colliderManager.isFree(new GridPoint2(2, 3))),
                () -> assertFalse(colliderManager.isFree(new GridPoint2(3, 2)))
        );
    }
}