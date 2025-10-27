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

// Интерфейс генератора уровня
interface LevelGenerator {
    Level generate();
    Player getPlayer();
}

// Случайный генератор
class RandomLevelGenerator implements LevelGenerator {
    private final int width, height;
    private Player player;

    public RandomLevelGenerator(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Level generate() {
        Level level = new Level();
        GridPoint2 playerPos = new GridPoint2(1, 1);
        PlayerConfig config = new PlayerConfig(playerPos);
        player = new Player(config, level);
        
        // Добавляем несколько случайных деревьев
        new Tree(new TreeConfig(new GridPoint2(1, 3)), level);
        new Tree(new TreeConfig(new GridPoint2(3, 2)), level);
        new Tree(new TreeConfig(new GridPoint2(4, 4)), level);
        
        return level;
    }

    public Player getPlayer() { return player; }
}

// Файловый генератор
class FromFileLevelGenerator implements LevelGenerator {
    private final String filePath;
    private Player player;

    public FromFileLevelGenerator(String filePath) {
        this.filePath = filePath;
    }

    public Level generate() {
        Level level = new Level();
        
        // Читаем файл
        FileHandle file = Gdx.files.internal(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }
        
        String[] lines = file.readString().split("\\r?\\n");
        List<GridPoint2> trees = new ArrayList<>();
        GridPoint2 playerPos = null;

        // Парсим данные
        for (int fileY = 0; fileY < lines.length; fileY++) {
            String line = lines[fileY].trim();
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                int gameY = lines.length - 1 - fileY; // Инвертируем Y
                
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

        // Создаем игрока
        PlayerConfig playerConfig = new PlayerConfig(playerPos);
        player = new Player(playerConfig, level);

        // Создаем деревья
        for (GridPoint2 treePos : trees) {
            TreeConfig treeConfig = new TreeConfig(treePos);
            new Tree(treeConfig, level);
        }

        return level;
    }

    public Player getPlayer() { return player; }
}