package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.collision.CollisionDetector;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.Tree;

import java.util.ArrayList;
import java.util.List;


public class LevelManager {
    private final TiledMapTileLayer groundLayer;
    private final Texture treeTexture;
    private final Texture tankTexture;
    private final CollisionDetector collisionDetector;
    private final List<Tree> createdTrees;
    
    public LevelManager(TiledMapTileLayer groundLayer, Texture treeTexture, 
                       Texture tankTexture, CollisionDetector collisionDetector) {
        this.groundLayer = groundLayer;
        this.treeTexture = treeTexture;
        this.tankTexture = tankTexture;
        this.collisionDetector = collisionDetector;
        this.createdTrees = new ArrayList<>();
    }
    
    // Случайная генерация уровня
    public Tank createRandomLevel(float obstacleDensity, float movementSpeed) {
        clearLevel(); // Очищаем предыдущий уровень
        
        LevelGenerator generator = new LevelGenerator(10, 8);
        List<GridPoint2> obstaclePositions = generator.generateRandomObstacles(obstacleDensity);
        
        // Создаем деревья и сохраняем ссылки
        for (GridPoint2 position : obstaclePositions) {
            Tree tree = new Tree(treeTexture, groundLayer, position);
            createdTrees.add(tree);
            collisionDetector.addObstacle(tree);
        }
        
        GridPoint2 startPosition = generator.generateRandomStartPosition();
        return new Tank(tankTexture, groundLayer, startPosition, collisionDetector, movementSpeed);
    }
    
    // Создание уровеня из файла

    public Tank createLevelFromFile(String filename, float movementSpeed) {
        clearLevel(); 
        
        LevelLoader loader = new LevelLoader();
        LevelData levelData = loader.loadLevel(filename);
        

        for (GridPoint2 position : levelData.getTreePositions()) {
            Tree tree = new Tree(treeTexture, groundLayer, position);
            createdTrees.add(tree);
            collisionDetector.addObstacle(tree);
        }
        
        return new Tank(tankTexture, groundLayer, levelData.getPlayerStartPosition(), 
                       collisionDetector, movementSpeed);
    }
    

    public void renderTrees(Batch batch) {
        for (Tree tree : createdTrees) {
            tree.render(batch);
        }
    }
    

    public void dispose() {
        for (Tree tree : createdTrees) {
            tree.dispose();
        }
        createdTrees.clear();
        treeTexture.dispose();
        tankTexture.dispose();
    }
    

    public void clearLevel() {
        for (Tree tree : createdTrees) {
            collisionDetector.removeObstacle(tree);
        }
        createdTrees.clear();
    }
    

    public List<Tree> getTrees() {
        return new ArrayList<>(createdTrees);
    }
}