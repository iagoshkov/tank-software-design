package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.Drawable;
import ru.mipt.bit.platformer.objects.Tank;

import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class TxtParser implements FileParser{

    @Override
    public Map<GridPoint2, Character> parseCoordinatesFromFile(String filePath) {
        Map<GridPoint2, Character> objectCoordinates = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int y = 0;
            while ((line = reader.readLine()) != null) {
                for (int x = 0; x < line.length(); x++) {
                    char character = line.charAt(x);
                    if (CharToDrawableConverter.isCharExists(character)) {
                        objectCoordinates.put(new GridPoint2(x, y), character);
                    }
                }
                y++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectCoordinates;
    }
}