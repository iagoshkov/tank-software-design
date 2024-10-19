package ru.mipt.bit.platformer.keys;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.objects.Ghost;
import ru.mipt.bit.platformer.objects.Movable;
import ru.mipt.bit.platformer.objects.GameObject;
import ru.mipt.bit.platformer.objects.Wall;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.UP;
import static org.junit.jupiter.api.Assertions.*;

class UPTest {
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
        List<Movable> movables = new ArrayList<>(List.of(ghost));

        UP up = new UP
                (
                        objects,
                        movables,
                        new int[]{W, UP},
                        Direction.UP
                );
//        assertTrue(up.existCollisions(ghost));
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
        List<Movable> movables = new ArrayList<>(List.of(ghost));

        UP up = new UP
                (
                        objects,
                        movables,
                        new int[]{W, UP},
                        Direction.UP
                );
//        assertFalse(up.existCollisions(ghost));
    }
}