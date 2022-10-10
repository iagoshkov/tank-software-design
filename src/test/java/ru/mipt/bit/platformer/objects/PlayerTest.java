package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerTest {
    static Player player;
    static ArrayList<OnScreenObject> obstacles = new ArrayList<>(2);

    @BeforeAll
    static void setup() {
        player = new Player(new GridPoint2(0, 0));
        obstacles.add(new OnScreenObject(new GridPoint2(1, 1)));
        obstacles.add(new OnScreenObject(new GridPoint2(1, 2)));
    }

    @Test
    void update() {
        player.update(new GridPoint2(1, 1), obstacles, 1, 3f);
        player.update(new GridPoint2(2, 6), obstacles, 1, 1f);
        assertThat(player.getCoordinates()).isEqualTo(new GridPoint2(0, 0));
        player.update(new GridPoint2(0, 0), obstacles, 0.5f, 1f);
        assertThat(player.getMovementProgress()).isEqualTo(0.5f);
        player.update(new GridPoint2(0, 0), obstacles, 0.5f, 1f);
        assertThat(player.getCoordinates()).isEqualTo(new GridPoint2(2, 6));
    }
}