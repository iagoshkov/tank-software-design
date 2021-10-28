package ru.mipt.bit.platformer.loaders;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class GameObjectMapRandomGeneratorTest {
    @ParameterizedTest
    @CsvSource({
            "10, 8, 10, 1",
            "8, 5, 3, 1",
            "15, 10, 0, 1",
            "15, 10, 20, 2"
    })
    public void testLoadMap(int height, int width, int nTrees, int nBots) {
        Function<GridPoint2, Boolean> checkPositionCorrectness = (GridPoint2 point) -> {
            return 0 <= point.x && point.x < width && 0 <= point.y && point.y < height;
        };

        var loader = new GameObjectMapRandomGenerator(height, width, nTrees, nBots);
        loader.loadMap();

        assertTrue(checkPositionCorrectness.apply(loader.getPlayerPosition()));
        assertEquals(nTrees, loader.getTreePositions().size());
        assertTrue(loader.getTreePositions().stream().allMatch(checkPositionCorrectness::apply));
    }
}
