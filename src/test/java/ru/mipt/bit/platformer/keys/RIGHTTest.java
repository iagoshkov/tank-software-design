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
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static org.junit.jupiter.api.Assertions.*;

class RIGHTTest {
    @Test
    void existCollisions() {
        Wall wall = new Wall
                (
                        new GridPoint2(2, 2)
                );
        Ghost ghost = new Ghost
                (
                        new GridPoint2(1, 2),
                        1f,
                        1f
                );

        List<GameObject>   objects = new ArrayList<>(List.of( wall));
        List<Movable> movables = new ArrayList<>(List.of(ghost));

        RIGHT right = new RIGHT
                (
                        objects,
                        movables,
                        new int[]{D, RIGHT},
                        Direction.RIGHT
                );
//        assertTrue(right.existCollisions(ghost));
    }

    @Test
    void notExistCollisions() {
        Wall wall = new Wall
                (
                        new GridPoint2(1, 2)
                );
        Ghost ghost = new Ghost
                (
                        new GridPoint2(2, 2),
                        1f,
                        1f
                );

        List<GameObject>   objects = new ArrayList<>(List.of( wall));
        List<Movable> movables = new ArrayList<>(List.of(ghost));

        RIGHT right = new RIGHT
                (
                        objects,
                        movables,
                        new int[]{D, RIGHT},
                        Direction.RIGHT
                );
//        assertFalse(right.existCollisions(ghost));
    }
}