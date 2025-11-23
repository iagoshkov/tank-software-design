package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Level;
import ru.mipt.bit.platformer.configs.PlayerConfig;
import ru.mipt.bit.platformer.configs.TreeConfig;
import ru.mipt.bit.platformer.objects.Player;
import ru.mipt.bit.platformer.objects.Tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelLoader {
    
    public static class LevelData {
        public PlayerConfig playerConfig;
        public List<TreeConfig> treeConfigs;
        
        public LevelData(PlayerConfig playerConfig, List<TreeConfig> treeConfigs) {
            this.playerConfig = playerConfig;
            this.treeConfigs = treeConfigs;
        }
    }
    
    public static LevelData loadFromFile(String filename) {
        List<String> lines = new ArrayList<>();
        GridPoint2 playerPosition = null;
        List<TreeConfig> trees = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(Gdx.files.internal(filename).reader())) {
            String line;
            int y = 0;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
                int actualY = lines.size() - 1 - y;
                
                for (int x = 0; x < line.length(); x++) {
                    char c = line.charAt(x);
                    if (c == 'X') {
                        playerPosition = new GridPoint2(x, actualY);
                    } else if (c == 'T') {
                        trees.add(new TreeConfig(new GridPoint2(x, actualY)));
                    }
                }
                y++;
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load level from file: " + filename, e);
        }
        
        if (playerPosition == null) {
            throw new RuntimeException("No player starting position (X) found in level file: " + filename);
        }
        
        PlayerConfig playerConfig = new PlayerConfig(playerPosition);
        return new LevelData(playerConfig, trees);
    }

    public static LevelData generateRandomLevel(int width, int height, float obstacleDensity) {
        Random random = new Random();
        List<TreeConfig> trees = new ArrayList<>();
        GridPoint2 playerPosition;
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (random.nextFloat() < obstacleDensity) {
                    trees.add(new TreeConfig(new GridPoint2(x, y)));
                }
            }
        }
        do {
            playerPosition = new GridPoint2(
                random.nextInt(width),
                random.nextInt(height)
            );
        } while (isPositionOccupied(playerPosition, trees));
        
        PlayerConfig playerConfig = new PlayerConfig(playerPosition);
        return new LevelData(playerConfig, trees);
    }
    
    private static boolean isPositionOccupied(GridPoint2 position, List<TreeConfig> trees) {
        for (TreeConfig tree : trees) {
            if (tree.getInitialPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }
}