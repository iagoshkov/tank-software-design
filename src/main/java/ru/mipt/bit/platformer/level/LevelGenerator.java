package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class LevelGenerator {
    private final int width;
    private final int height;
    private final Random random;
    
    public LevelGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.random = new Random();
    }
    
    /**
     * Генератор случайныз препятствий
     * obstacleDensity: плотность препятствий от 0.0 до 1.0
     */
    public List<GridPoint2> generateRandomObstacles(float obstacleDensity) {
        List<GridPoint2> obstacles = new ArrayList<>();
        int totalCells = width * height;
        int obstacleCount = (int) (totalCells * obstacleDensity);
        
        // Гарантия, что игрок сможет двигаться
        for (int i = 0; i < obstacleCount; i++) {
            GridPoint2 obstaclePos = new GridPoint2(
                random.nextInt(width),
                random.nextInt(height)
            );
            
            if (!isNearStartPosition(obstaclePos)) {
                obstacles.add(obstaclePos);
            }
        }
        
        return obstacles;
    }
    

    public GridPoint2 generateRandomStartPosition() {
        return new GridPoint2(
            random.nextInt(width / 2), 
            random.nextInt(height / 2)  
        );
    }
    
    private boolean isNearStartPosition(GridPoint2 pos) {
        GridPoint2 startArea = new GridPoint2(width / 4, height / 4);
        return pos.x < startArea.x && pos.y < startArea.y;
    }
}