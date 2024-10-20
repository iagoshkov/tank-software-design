package ru.mipt.bit.platformer.keys;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.levels.BorderLevel;
import ru.mipt.bit.platformer.objects.Ghost;
import ru.mipt.bit.platformer.objects.GameObject;
import ru.mipt.bit.platformer.objects.Wall;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveUpTest {
    @Test
    void existCollisions() {
        Wall wall = new Wall
                (
                        new GridPoint2(2, 2)
                );
        Ghost ghost = new Ghost
                (
                        new GridPoint2(2, 1),
                        1f,
                        1f
                );

        List<GameObject>   objects = new ArrayList<>(List.of( wall));
        assertFalse(ghost.canMoveToDirection(Direction.UP, objects, new BorderLevel(7, 5)));
    }

    @Test
    void notExistCollisions() {
        Wall wall = new Wall
                (
                        new GridPoint2(2, 2)
                );
        Ghost ghost = new Ghost
                (
                        new GridPoint2(2, 3),
                        1f,
                        1f
                );

        List<GameObject> objects = new ArrayList<>(List.of( wall));
        assertTrue(ghost.canMoveToDirection(Direction.UP, objects, new BorderLevel(7, 5)));
    }
}