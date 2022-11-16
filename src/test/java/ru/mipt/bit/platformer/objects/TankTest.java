package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.movementCommand.TankAction;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class TankTest {
    static ArrayList<Tank> tanks = new ArrayList<>(2);
    static ArrayList<OnScreenObject> obstacles = new ArrayList<>(2);
    static float TANK_SPEED = 0.4f;
    @BeforeAll
    static void setup() {
        tanks.add(new Tank(new GridPoint2(0, 0), TANK_SPEED, new GridPoint2(5, 5)));
        tanks.add(new Tank(new GridPoint2(1, 0), TANK_SPEED, new GridPoint2(5, 5)));
        obstacles.add(new OnScreenObject(new GridPoint2(1, 1)));
        obstacles.add(new OnScreenObject(new GridPoint2(1, 2)));
    }

    @Test
    void update() {
        tanks.get(0).update(TankAction.MOVE_UP, obstacles, tanks, 1f);
        tanks.get(0).update(TankAction.MOVE_RIGHT, obstacles, tanks, 1f);
        tanks.get(0).update(TankAction.WAIT, obstacles, tanks, 1f);
        assertThat(tanks.get(0).getCoordinates()).isEqualTo(new GridPoint2(0, 1));
        tanks.get(0).update(TankAction.MOVE_UP, obstacles, tanks, 1);
        assertThat(tanks.get(0).getCoordinates()).isEqualTo(new GridPoint2(0, 1));
        tanks.get(0).update(TankAction.WAIT, obstacles, tanks, 0.5f);
        assertThat(tanks.get(0).getMovementProgress()).isEqualTo(1f);
        tanks.get(0).update(TankAction.WAIT, obstacles, tanks, 0.5f);
        assertThat(tanks.get(0).getCoordinates()).isEqualTo(new GridPoint2(0, 2));
    }
}