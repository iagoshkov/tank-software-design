package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


class LevelGeneratorTest {
    static LevelGenerator fromFile;
    static LevelGenerator rand;
    @BeforeAll
    static void setUp(){
        try {
            fromFile = new LevelGenerator("src/test/java/ru/mipt/bit/platformer/level_test.txt");
        } catch (Exception ignored) {}
        rand = new LevelGenerator(new int[]{5, 5}, 5);
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
        checkPlayerNotOverlapsObstacles(fromFile.getPlayerCoordinates(), fromFile.getObstaclesCoordinates());
        checkPlayerNotOverlapsObstacles(rand.getPlayerCoordinates(), rand.getObstaclesCoordinates());
    }

    private static void checkPlayerNotOverlapsObstacles(GridPoint2 playerFromFile, ArrayList<GridPoint2> obstaclesFromFile) {
        for (var obstacle : obstaclesFromFile) {
            assertThat(obstacle).isNotEqualTo(playerFromFile);
        }
    }
}