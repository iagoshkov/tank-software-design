package ru.mipt.bit.platformer.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TxtParserTest {

    @Test
    void parseCoordinatesFromFile() {
        TxtParser parser = new TxtParser();
        var map = parser.parseCoordinatesFromFile("src/main/res/level_test.txt");
        assertEquals(12, map.size());
    }
}