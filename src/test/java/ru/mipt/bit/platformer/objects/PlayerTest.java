package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerTest {
    static ArrayList<Player> players = new ArrayList<>(2);
    static ArrayList<OnScreenObject> obstacles = new ArrayList<>(2);

    @BeforeAll
    static void setup() {
        players.add(new Player(new GridPoint2(0, 0), new GridPoint2(5, 5)));
        players.add(new Player(new GridPoint2(1, 0), new GridPoint2(5, 5)));
        obstacles.add(new OnScreenObject(new GridPoint2(1, 1)));
        obstacles.add(new OnScreenObject(new GridPoint2(1, 2)));
    }

    @Test
    void update() {
        players.get(0).update(new GridPoint2(1, 1), obstacles, players, 1, 3f);
        players.get(0).update(new GridPoint2(1, 0), obstacles, players, 1, 3f);
        players.get(0).update(new GridPoint2(0, 0), obstacles, players, 1f, 1f);
        assertThat(players.get(0).getCoordinates()).isEqualTo(new GridPoint2(0, 0));
        players.get(0).update(new GridPoint2(0, 1), obstacles, players, 1, 1f);
        assertThat(players.get(0).getCoordinates()).isEqualTo(new GridPoint2(0, 0));
        players.get(0).update(new GridPoint2(0, 0), obstacles, players, 0.5f, 1f);
        assertThat(players.get(0).getMovementProgress()).isEqualTo(0.5f);
        players.get(0).update(new GridPoint2(0, 0), obstacles, players, 0.5f, 1f);
        assertThat(players.get(0).getCoordinates()).isEqualTo(new GridPoint2(0, 1));
    }
}