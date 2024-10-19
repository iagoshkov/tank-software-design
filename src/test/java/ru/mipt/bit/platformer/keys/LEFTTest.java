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

import static org.junit.jupiter.api.Assertions.*;

class LEFTTest {

    @Test
    void existCollisions() {
        Wall wall = new Wall
                (
                        new GridPoint2(2, 2)
                );
        Ghost ghost = new Ghost
                (
                        new GridPoint2(3, 2),
                        1f,
                        1f
                );

        List<GameObject>   objects = new ArrayList<>(List.of( wall));
        List<Movable> movables = new ArrayList<>(List.of(ghost));

        LEFT left = new LEFT
                (
                        objects,
                        movables,
                        new int[]{A, LEFT},
                        Direction.LEFT
                );
//        assertTrue(left.existCollisions(ghost));
    }

    @Test
    void notExistCollisions() {
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

        LEFT left = new LEFT
                (
                        objects,
                        movables,
                        new int[]{A, LEFT},
                        Direction.LEFT
                );
//        assertFalse(left.existCollisions(ghost));
    }

    @Test
    void actionCheckDestinationCoordinates() {
        Wall wall = new Wall
                (
                        new GridPoint2(2, 2)
                );
        Ghost ghost = new Ghost
                (
                        new GridPoint2(4, 2),
                        1f,
                        1f
                );

        List<GameObject>   objects = new ArrayList<>(List.of( wall));
        List<Movable> movables = new ArrayList<>(List.of(ghost));

        LEFT left = new LEFT
                (
                        objects,
                        movables,
                        new int[]{A, LEFT},
                        Direction.LEFT
                );
//        left.action();
//        assertEquals(new GridPoint2(3,2), ghost.getDestinationCoordinates());
    }

    @Test
    void actionCheckRotation() {
        Wall wall = new Wall
                (
                        new GridPoint2(2, 2)
                );
        Ghost ghost = new Ghost
                (
                        new GridPoint2(4, 2),
                        1f,
                        1f
                );

        List<GameObject>   objects = new ArrayList<>(List.of( wall));
        List<Movable> movables = new ArrayList<>(List.of(ghost));

        LEFT left = new LEFT
                (
                        objects,
                        movables,
                        new int[]{A, LEFT},
                        Direction.LEFT
                );
//        left.action();
//        assertEquals(-180f, ghost.getRotation());
    }

    @Test
    void actionCheckMovementProgress() {
        Wall wall = new Wall
                (
                        new GridPoint2(2, 2)
                );
        Ghost ghost = new Ghost
                (
                        new GridPoint2(4, 2),
                        1f,
                        1f
                );

        List<GameObject>   objects = new ArrayList<>(List.of( wall));
        List<Movable> movables = new ArrayList<>(List.of(ghost));

        LEFT left = new LEFT
                (
                        objects,
                        movables,
                        new int[]{A, LEFT},
                        Direction.LEFT
                );
//        left.action();
//        assertEquals(0, ghost.getMovementProgress());
    }
}