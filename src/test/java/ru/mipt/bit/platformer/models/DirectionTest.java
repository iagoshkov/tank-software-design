package ru.mipt.bit.platformer.models;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void testDirectionVectorsAndRotation() {
        assertEquals(new Vector2(0, 1), Direction.UP.getVector());
        assertEquals(90f, Direction.UP.getRotation());

        assertEquals(new Vector2(0, -1), Direction.DOWN.getVector());
        assertEquals(270f, Direction.DOWN.getRotation());

        assertEquals(new Vector2(-1, 0), Direction.LEFT.getVector());
        assertEquals(180f, Direction.LEFT.getRotation());

        assertEquals(new Vector2(1, 0), Direction.RIGHT.getVector());
        assertEquals(0f, Direction.RIGHT.getRotation());
    }
}
