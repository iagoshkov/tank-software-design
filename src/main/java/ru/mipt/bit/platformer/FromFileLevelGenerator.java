package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.configs.PlayerConfig;
import ru.mipt.bit.platformer.configs.TreeConfig;
import ru.mipt.bit.platformer.objects.Player;
import ru.mipt.bit.platformer.objects.Tree;

import java.util.ArrayList;
import java.util.List;

public class FromFileLevelGenerator implements LevelGenerator {
    private final String filePath;
    private Player player;

    public FromFileLevelGenerator(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Level generate() {
        Level level = new Level();
        
        FileHandle file = Gdx.files.internal(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }
        
        String[] lines = file.readString().split("\\r?\\n");
        List<GridPoint2> trees = new ArrayList<>();
        GridPoint2 playerPos = null;

        for (int fileY = 0; fileY < lines.length; fileY++) {
            String line = lines[fileY].trim();
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                int gameY = lines.length - 1 - fileY; 
                
                if (c == 'X') {
                    playerPos = new GridPoint2(x, gameY);
                } else if (c == 'T') {
                    trees.add(new GridPoint2(x, gameY));
                }
            }
        }

        if (playerPos == null) {
            throw new IllegalStateException("Player not found in level file");
        }

        PlayerConfig playerConfig = new PlayerConfig(playerPos);
        player = new Player(playerConfig, level);

        for (GridPoint2 treePos : trees) {
            TreeConfig treeConfig = new TreeConfig(treePos);
            new Tree(treeConfig, level);
        }

        return level;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}