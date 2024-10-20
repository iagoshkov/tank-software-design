package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

import java.io.IOException;
import java.util.Map;

public interface FileParser {
    Map<GridPoint2, Character> parseCoordinatesFromFile(String filePath) throws IOException;
}
