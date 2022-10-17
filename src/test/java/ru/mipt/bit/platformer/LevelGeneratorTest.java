package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.generator.FromFileGenerator;
import ru.mipt.bit.platformer.generator.LevelGenerator;
import ru.mipt.bit.platformer.generator.RandomGenerator;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;


class LevelGeneratorTest {
    static LevelGenerator fromFile;
    static LevelGenerator rand;
    @BeforeAll
    static void setUp(){
        try {
            fromFile = new FromFileGenerator("src/test/java/ru/mipt/bit/platformer/level_test.txt");
        } catch (Exception ignored) {}
        rand = new RandomGenerator(5, 5, 5, 3);
    }

    @Test
    void getObstaclesCoordinates() {
        var obstaclesFromFile = fromFile.getObstaclesCoordinates();
        var obstaclesRandom = rand.getObstaclesCoordinates();

        assertThat(obstaclesFromFile.size()).isEqualTo(8);
        assertThat(obstaclesRandom.size()).isEqualTo(5);

        checkObstaclesNonOverlapping(obstaclesFromFile);
        checkObstaclesNonOverlapping(obstaclesRandom);
    }

    private static void checkObstaclesNonOverlapping(ArrayList<GridPoint2> obstaclesFromFile) {
        for (int i = 0; i < obstaclesFromFile.size(); ++i) {
            for (int j = 0; j < obstaclesFromFile.size(); ++j) {
                if (i == j) continue;
                assertThat(obstaclesFromFile.get(i)).isNotEqualTo(obstaclesFromFile.get(j));
            }
        }
    }

    @Test
    void getPlayerCoordinates() {
        checkObjectsNotOverlap(fromFile.getPlayersCoordinates(), fromFile.getObstaclesCoordinates());
        checkObjectsNotOverlap(rand.getPlayersCoordinates(), rand.getObstaclesCoordinates());
    }

    private static void checkObjectsNotOverlap(ArrayList<GridPoint2> players, ArrayList<GridPoint2> obstacles) {
        for (var obstacle : obstacles) {
            for (var player : players) {
                assertThat(obstacle).isNotEqualTo(player);
            }
        }

        for (int i = 0; i < obstacles.size(); ++i) {
            for (int j = 0; j < obstacles.size(); ++j) {
                if (i == j) continue;
                assertThat(obstacles.get(i)).isNotEqualTo(obstacles.get(j));
            }
        }

        for (int i = 0; i < players.size(); ++i) {
            for (int j = 0; j < players.size(); ++j) {
                if (i == j) continue;
                assertThat(players.get(i)).isNotEqualTo(players.get(j));
            }
        }
    }
}