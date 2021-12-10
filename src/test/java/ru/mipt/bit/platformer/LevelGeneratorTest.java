package ru.mipt.bit.platformer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class LevelGeneratorTest {

//    @Test
//    void generateRandomCoordinates_ShouldReturnEmptyTrees() {
//        LevelGenerator levelGenerator = new LevelGenerator();
//        levelGenerator.generateRandomCoordinates(0);
//
//        assertTrue(levelGenerator.getTreeCoordinates().isEmpty());
//    }
//
//    @ParameterizedTest
//    @CsvSource({"0,0", "15,15", "79,79", "90,79"})
//    void generateRandomCoordinates_ShouldReturnGivenNumberOfTrees(int n, int expected) {
//        LevelGenerator levelGenerator = new LevelGenerator();
//        levelGenerator.generateRandomCoordinates(n);
//
//        assertEquals(levelGenerator.getTreeCoordinates().size(), expected);
//    }
//
//    @ParameterizedTest
//    @ValueSource(ints = {0, 5, 80})
//    void generateRandomCoordinates_ShouldReturnOneTank(int n) {
//        LevelGenerator levelGenerator = new LevelGenerator();
//        levelGenerator.generateRandomCoordinates(n);
//
//        assertEquals(levelGenerator.getTankCoordinates().size(), 1);
//    }
}