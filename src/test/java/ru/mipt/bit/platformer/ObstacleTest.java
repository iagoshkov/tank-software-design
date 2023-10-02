package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.objects.Obstacle;

import static org.junit.jupiter.api.Assertions.*;

class ObstacleTest {

    @Test
    public void testGetCoordinates() {
        GridPoint2 initialCoordinates = new GridPoint2(1, 2);
        Obstacle obstacle = new Obstacle(initialCoordinates);

        GridPoint2 result = obstacle.getCoordinates();

        assertEquals(initialCoordinates, result);
    }

}