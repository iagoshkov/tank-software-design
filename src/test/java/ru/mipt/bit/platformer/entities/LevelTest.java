package ru.mipt.bit.platformer.entities;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.mipt.bit.platformer.graphics.GraphicsController;
import ru.mipt.bit.platformer.instructions.Direction;
import ru.mipt.bit.platformer.instructions.InputController;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest {

    @Test
    void TestLevelAddTankAndTreeThenMoveTank() {
        Tank tank = new Tank(new GridPoint2(2, 3), Direction.RIGHT, 1);
        MapObject object = new Tree(new GridPoint2(1, 3));

        GraphicsController graphicsController = Mockito.mock(GraphicsController.class);

        InputController inputController = Mockito.mock(InputController.class);
        Mockito.when(inputController.getInstruction())
                .thenReturn(Map.entry(Direction.UP, object))
                .thenReturn(Map.entry(Direction.LEFT, tank))
                .thenReturn(Map.entry(Direction.UP, tank));

        Level level = new Level(tank, inputController, graphicsController);

        level.add(object);
        level.moveObjects();
        level.moveObjects();
        level.moveObjects();

        assertEquals(new GridPoint2(1, 3), object.getCoordinates());
        assertEquals(new GridPoint2(2, 4), tank.getDestinationCoordinates());
    }
}