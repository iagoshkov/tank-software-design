package ru.mipt.bit.platformer.loaders;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameObjectMapFileLoaderTest {
    @Test
    public void testLoadMap() {
        var loader = new GameObjectMapFileLoader(8, 10, "/gameObjectMap");
        assertDoesNotThrow(loader::loadMap);
        assertEquals(new GridPoint2(5, 1), loader.getPlayerPosition());

        List<GridPoint2> actualTreePositions = new ArrayList<>(loader.getTreePositions());
        assertEquals(7, actualTreePositions.size());

        List<GridPoint2> expectedTreePositions = List.of(
                new GridPoint2(0, 2),
                new GridPoint2(3, 5),
                new GridPoint2(6, 2),
                new GridPoint2(6, 5),
                new GridPoint2(8, 4),
                new GridPoint2(9, 2),
                new GridPoint2(9, 3)
        );
        actualTreePositions.sort(Comparator.comparing((GridPoint2 p) -> p.x).thenComparing((GridPoint2 p) -> p.y));

        assertEquals(expectedTreePositions, actualTreePositions);
    }
}
