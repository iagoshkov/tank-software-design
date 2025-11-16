package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

import main.java.ru.mipt.bit.platformer.observer.GameLevelObservable;
import ru.mipt.bit.platformer.collision.CollisionDetector;
import ru.mipt.bit.platformer.controller.AITankController;
import ru.mipt.bit.platformer.controller.ToggleHealthDisplayCommand;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class LevelManager {
    private final TiledMapTileLayer groundLayer;
    private final Texture treeTexture;
    private final Texture tankTexture;
    private final CollisionDetector collisionDetector;
    private final List<Tree> createdTrees;
    private ToggleHealthDisplayCommand toggleHealthCommand;
    private final GameLevelObservable levelObservable;
    private final Texture bulletTexture;
    
    public LevelManager(TiledMapTileLayer groundLayer, Texture treeTexture, 
                       Texture tankTexture, Texture bulletTexture, 
                       CollisionDetector collisionDetector, GameLevelObservable levelObservable) {        this.groundLayer = groundLayer;
        this.treeTexture = treeTexture;
        this.tankTexture = tankTexture;
        this.collisionDetector = collisionDetector;
        this.createdTrees = new ArrayList<>();
        this.createdTanks = new ArrayList<>();
        this.aiControllers = new ArrayList<>();
        this.bulletTexture = bulletTexture;
        this.levelObservable = levelObservable;
    }

    public List<Tank> getAllTanks() {
        return new ArrayList<>(createdTanks);
    }

    // метод для получения команды переключения здоровья
    public ToggleHealthDisplayCommand getToggleHealthCommand() {
        if (toggleHealthCommand == null) {
            toggleHealthCommand = new ToggleHealthDisplayCommand(getAllTanks());
        }
        return toggleHealthCommand;
    }

    public List<Tank> getAliveTanks() {
        return createdTanks.stream()
                .filter(Tank::isAlive)
                .collect(Collectors.toList());
    }

    public List<Tank> createRandomTanks(int tankCount, float movementSpeed) {
        List<Tank> tanks = new ArrayList<>();
        LevelGenerator generator = new LevelGenerator(10, 8);
        
        for (int i = 0; i < tankCount; i++) {
            GridPoint2 position;
            boolean positionValid;
            int attempts = 0;
            
            do {
                position = generator.generateRandomStartPosition();
                positionValid = !collisionDetector.isPositionBlocked(position);
                attempts++;
            } while (!positionValid && attempts < 100);
            
            if (positionValid) {
                 Tank tank = new Tank(tankTexture, groundLayer, position, collisionDetector, 
                               movementSpeed, bulletTexture, levelObservable);
                tank.setPlayerControlled(false);
                
                AITankController aiController = new AITankController(tank);
                tank.setAIController(aiController);
                
                tanks.add(tank);
                createdTanks.add(tank);
                aiControllers.add(aiController);
                collisionDetector.addMovingTank(tank);
            }
            // Регистрируем танк в наблюдателе
            levelObservable.addGameObject(tank);
        }

        
        return tanks;
    }

    public void renderTanks(Batch batch) {
        for (Tank tank : createdTanks) {
            tank.render(batch);
        }
    }

    // метод инициализации уровня
    public void initializeLevel() {
        // Регистрруем все деревья
        for (Tree tree : createdTrees) {
            levelObservable.addGameObject(tree);
        }
        
        // Регистрируем все такни
        for (Tank tank : createdTanks) {
            levelObservable.addGameObject(tank);
        }
        
        // Уведомляем о завершении инициализации
        levelObservable.notifyLevelInitialized();
    }

    public void updateTanks(float deltaTime) {
        // Удаляем мертвые танки
        Iterator<Tank> iterator = createdTanks.iterator();
        while (iterator.hasNext()) {
            Tank tank = iterator.next();
            if (!tank.isAlive()) {
                collisionDetector.removeMovingTank(tank);
                iterator.remove();
            }
        }
        
        // Обновляем живые танки
        for (Tank tank : createdTanks) {
            if (tank.isAlive()) {
                tank.update(deltaTime);
            }
        }
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
        for (Tank tank : createdTanks) {
            tank.dispose();
        }
        createdTrees.clear();
        createdTanks.clear();
        aiControllers.clear();
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