package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.configs.PlayerConfig;
import ru.mipt.bit.platformer.controllers.AiInputController;
import ru.mipt.bit.platformer.controllers.PlayerInputController;
import ru.mipt.bit.platformer.objects.GameObject;
import ru.mipt.bit.platformer.objects.Player;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.tests.RunConfigsTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {
    private Batch batch;
    private Level level;
    private Player playerTank;
    private List<Tank> aiTanks;
    private InputHandler playerInputHandler;
    private List<InputHandler> aiInputHandlers;
    private Random random;

    @Override
    public void create() {
    batch = new SpriteBatch();
    aiTanks = new ArrayList<>();
    aiInputHandlers = new ArrayList<>();
    random = new Random();
    
    // Используем файловый генератор уровня
    LevelGenerator generator = new FromFileLevelGenerator("src/assets/levels/level1.txt");
    level = generator.generate();
    playerTank = generator.getPlayer();
    
    // Создаем ИИ танки
    createAiTanks(3); // 3 ИИ танка
    
    playerInputHandler = new InputHandler(playerTank, new PlayerInputController(), level);
    
    // Создаем обработчики для ИИ танков
    for (Tank aiTank : aiTanks) {
        aiInputHandlers.add(new InputHandler(aiTank, new AiInputController(), level));
    }
}


    private void createAiTanks(int count) {
        int tanksCreated = 0;
        int maxAttempts = 100; // Защита от бесконечного цикла
        
        while (tanksCreated < count && maxAttempts > 0) {
            // Генерируем случайную позицию в пределах уровня
            GridPoint2 randomPosition = new GridPoint2(
                random.nextInt(level.getGroundLayer().getWidth()),
                random.nextInt(level.getGroundLayer().getHeight())
            );
            if (isPositionAvailableForTank(randomPosition)) {
                PlayerConfig aiTankConfig = new PlayerConfig(
                    "src/main/resources/images/tank_blue.png", 
                    randomPosition,
                    0.9f // скорость
                );
        
                Tank aiTank = new Tank(aiTankConfig, level, false);
                aiTanks.add(aiTank);
                tanksCreated++;
            }
            
            maxAttempts--;
        }
        
        if (tanksCreated < count) {
            System.out.println("Warning: Created only " + tanksCreated + " AI tanks out of " + count);
        }
    }

    private boolean isPositionAvailableForTank(GridPoint2 position) {
    if (!level.isPositionValid(position)) {
        return false;
    }
    
    if (playerTank.getCoordinates().equals(position) || 
        (playerTank.isMoving() && playerTank.getDestinationCoordinates().equals(position))) {
        return false;
    }
    
    for (Tank aiTank : aiTanks) {
        if (aiTank.getCoordinates().equals(position) || 
            (aiTank.isMoving() && aiTank.getDestinationCoordinates().equals(position))) {
            return false;
        }
    }
    
    if (level.isPositionBlockedByTree(position)) {
        return false;
    }
    
    // Проверяем, что позиция не зарезервирована
    if (level.isPositionOccupied(position)) {
        return false;
    }
    
    return true;
}


    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        
        float deltaTime = Gdx.graphics.getDeltaTime();
        
        // Сначала обновляем все танки (завершаем текущее движение)
        playerTank.update(deltaTime);
        for (Tank aiTank : aiTanks) {
            if (aiTank != null) {
                aiTank.update(deltaTime);
            }
        }
        
        // Затем обрабатываем ввод (ИИ принимает решения после завершения движения)
        playerInputHandler.handleInput();
        for (int i = 0; i < aiTanks.size(); i++) {
            if (aiTanks.get(i) != null && !aiTanks.get(i).isMoving()) {
                aiInputHandlers.get(i).handleInput();
            }
        }
        
        batch.begin();
        level.render(batch);
        
        // Рендерим все игровые объекты
        for (GameObject gameObject : level.getGameObjects()) {
            gameObject.draw(batch);
        }
        
        batch.end();
        
        // Очистка уничтоженных танков
        cleanupDestroyedTanks();
    }

    private void cleanupDestroyedTanks() {
        // Удаляем уничтоженные танки из списков
        List<Tank> tanksToRemove = new ArrayList<>();
        List<InputHandler> handlersToRemove = new ArrayList<>();
        
        for (int i = 0; i < aiTanks.size(); i++) {
            Tank tank = aiTanks.get(i);
            // Здесь можно добавить логику проверки уничтожения танка
            // Например, если здоровье <= 0 или другая логика уничтожения
            // if (tank.isDestroyed()) {
            //     tanksToRemove.add(tank);
            //     handlersToRemove.add(aiInputHandlers.get(i));
            //     tank.dispose();
            // }
        }
        
        aiTanks.removeAll(tanksToRemove);
        aiInputHandlers.removeAll(handlersToRemove);
    }

    @Override
    public void resize(int width, int height) {
        // Можно добавить логику изменения размера окна при необходимости
    }

    @Override
    public void pause() {
        // Пауза игры
    }

    @Override
    public void resume() {
        // Возобновление игры
    }

    @Override
    public void dispose() {
        // Освобождение ресурсов
        playerTank.dispose();
        
        for (Tank aiTank : aiTanks) {
            if (aiTank != null) {
                aiTank.dispose();
            }
        }
        
        level.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
    if (args.length > 0 && "test".equals(args[0])) {
        // Запуск тестов
        new RunConfigsTest().main(args);
        return;
    }
    
    Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
    config.setTitle("Tank Game");
    config.setWindowedMode(1280, 1024);
    config.setResizable(false);
    
    new Lwjgl3Application(new GameDesktopLauncher(), config);
}
    
    // Геттеры для тестирования
    public Player getPlayerTank() {
        return playerTank;
    }
    
    public List<Tank> getAiTanks() {
        return new ArrayList<>(aiTanks);
    }
    
    public Level getLevel() {
        return level;
    }



}