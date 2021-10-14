package ru.mipt.bit.platformer.loaders;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class GameObjectMapRandomGeneratorTest {
    @ParameterizedTest
    @CsvSource({
            "10, 8, 10",
            "8, 5, 3,",
            "15, 10, 0",
            "15, 10, 20"
    })
    public void testLoadMap(int height, int width, int nTrees) {
        Function<GridPoint2, Boolean> checkPositionCorrectness = (GridPoint2 point) -> {
            return 0 <= point.x && point.x < width && 0 <= point.y && point.y < height;
        };

        var loader = new GameObjectMapRandomGenerator(height, width, nTrees);
        loader.loadMap();

        assertTrue(checkPositionCorrectness.apply(loader.getPlayerPosition()));
        assertEquals(nTrees, loader.getTreePositions().size());
        assertTrue(loader.getTreePositions().stream().allMatch(checkPositionCorrectness::apply));
    }
}
