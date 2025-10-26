package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


// Загрузчик уровня из файла

public class LevelLoader {
    
    public LevelData loadLevel(String filename) {
        List<GridPoint2> treePositions = new ArrayList<>();
        GridPoint2 playerStartPosition = null;
        int width = 0;
        int height = 0;
        
        try (BufferedReader reader = new BufferedReader(
             new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filename)))) {
            
            String line;
            int y = 0;
            
            while ((line = reader.readLine()) != null) {
                width = Math.max(width, line.length());
                
                for (int x = 0; x < line.length(); x++) {
                    char cell = line.charAt(x);
                    
                    switch (cell) {
                        case 'T':
                            treePositions.add(new GridPoint2(x, height - y - 1));
                            break;
                        case 'X':
                            playerStartPosition = new GridPoint2(x, height - y - 1);
                            break;
                        case '_':
                            break;
                    }
                }
                y++;
                height++;
            }
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to load level: " + filename, e);
        }
        
        if (playerStartPosition == null) {
            throw new RuntimeException("No player start position found in level file");
        }
        
        return new LevelData(treePositions, playerStartPosition, width, height);
    }
}