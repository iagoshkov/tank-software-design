package ru.mipt.bit.platformer.generators;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesGeneratorTest {

    @Test
    void generate() {
        CoordinatesGenerator coordinatesGenerator = new CoordinatesGenerator(new SimpleIntegerGenerator(), 7, 5);
        Set<GridPoint2> destination = new HashSet<>();
        coordinatesGenerator.generate(10, destination);
        assertEquals(10, destination.size());
        coordinatesGenerator.generate(10000, destination);
        assertEquals(coordinatesGenerator.getHeight() * coordinatesGenerator.getWidth(), destination.size());
    }
}