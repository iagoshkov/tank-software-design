package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.commands.ToggleHealthBarCommand;
import ru.mipt.bit.platformer.configs.PlayerConfig;
import ru.mipt.bit.platformer.controllers.AiInputController;
import ru.mipt.bit.platformer.controllers.PlayerInputController;
import ru.mipt.bit.platformer.decorators.HealthBarDecorator;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.objects.GameObject;
import ru.mipt.bit.platformer.objects.Player;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;

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
    private ToggleHealthBarCommand toggleHealthBarCommand;
    private boolean gameOver = false;
    private BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        aiTanks = new ArrayList<>();
        aiInputHandlers = new ArrayList<>();
        random = new Random();
        font = new BitmapFont();
        
        FromFileLevelGenerator generator = new FromFileLevelGenerator("src/assets/levels/level1.txt");
        level = generator.generate();
        playerTank = generator.getPlayer();
        
        createAiTanks(2);
        
        playerInputHandler = new InputHandler(playerTank, new PlayerInputController(), level);
        
        for (Tank aiTank : aiTanks) {
            aiInputHandlers.add(new InputHandler(aiTank, new AiInputController(), level));
        }
        
        toggleHealthBarCommand = new ToggleHealthBarCommand(level);
    }
    
    private void updateGameObjects(float deltaTime) {
        for (GameObject obj : level.getGameObjects()) {
            if (obj instanceof Bullet) {
                ((Bullet) obj).update(deltaTime);
            }
        }
        
        if (playerTank.isAlive()) {
            playerTank.update(deltaTime);
        }
        
        for (Tank aiTank : aiTanks) {
            if (aiTank != null && aiTank.isAlive()) {
                aiTank.update(deltaTime);
            }
        }
    }

    private void createAiTanks(int count) {
        int tanksCreated = 0;
        int maxAttempts = 100;
        
        while (tanksCreated < count && maxAttempts > 0) {
            GridPoint2 randomPosition = new GridPoint2(
                random.nextInt(level.getGroundLayer().getWidth()),
                random.nextInt(level.getGroundLayer().getHeight())
            );
            
            if (isPositionAvailableForTank(randomPosition)) {
                PlayerConfig aiTankConfig = new PlayerConfig(
                    "src/main/resources/images/tank_blue.png",
                    randomPosition,
                    0.9f
                );
                
                Tank aiTank = new Tank(aiTankConfig, level, false);
                aiTanks.add(aiTank);
                tanksCreated++;
            }
            
            maxAttempts--;
        }
    }

    private boolean isPositionAvailableForTank(GridPoint2 position) {
        if (!level.isPositionValid(position)) return false;
        if (playerTank.getCoordinates().equals(position) || 
            (playerTank.isMoving() && playerTank.getDestinationCoordinates().equals(position))) return false;
        
        for (Tank aiTank : aiTanks) {
            if (aiTank.getCoordinates().equals(position) || 
                (aiTank.isMoving() && aiTank.getDestinationCoordinates().equals(position))) return false;
        }
        
        for (GameObject obj : level.getGameObjects()) {
            if (obj instanceof Tree && obj.getCoordinates().equals(position)) return false;
        }
        
        if (level.isPositionOccupied(position)) return false;
        
        return true;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        
        float deltaTime = Gdx.graphics.getDeltaTime();
        
        if (!gameOver && !playerTank.isAlive()) {
            gameOver = true;
        }
        
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.L)) {
            toggleHealthBarCommand.execute();
        }
        
        if (!gameOver) {
            updateGameObjects(deltaTime);
            
            playerInputHandler.handleInput();
            for (int i = 0; i < aiTanks.size(); i++) {
                if (aiTanks.get(i) != null && aiTanks.get(i).isAlive() && !aiTanks.get(i).isMoving()) {
                    aiInputHandlers.get(i).handleInput();
                }
            }

            if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
                if (playerTank != null && playerTank.isAlive()) {
                    playerTank.shoot();
                }
            }
        }
        
        batch.begin();
        level.render(batch);
        
        for (GameObject gameObject : level.getGameObjects()) {
            if (gameObject instanceof Bullet) {
                gameObject.draw(batch);
            } else if (gameObject.isAlive()) {
                HealthBarDecorator decorator = new HealthBarDecorator(gameObject, gameObject);
                decorator.draw(batch);
            }
        }
        
        if (gameOver) {
            font.setColor(Color.RED);
            font.getData().setScale(3f);
            font.draw(batch, "GAME OVER", 500, 500);
        } else {
            font.setColor(Color.WHITE);
            font.getData().setScale(1.5f);
            font.draw(batch, "Health: " + playerTank.getHealth() + "/" + playerTank.getMaxHealth(), 20, 1000);
        }
        
        batch.end();
        
        cleanupDestroyedTanks();
    }

    private void cleanupDestroyedTanks() {
        List<Tank> tanksToRemove = new ArrayList<>();
        
        for (int i = 0; i < aiTanks.size(); i++) {
            Tank tank = aiTanks.get(i);
            if (tank != null && !tank.isAlive()) {
                tanksToRemove.add(tank);
                tank.dispose();
                level.getGameObjects().remove(tank);
            }
        }
        
        aiTanks.removeAll(tanksToRemove);
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        playerTank.dispose();
        
        for (Tank aiTank : aiTanks) {
            if (aiTank != null) {
                aiTank.dispose();
            }
        }
        
        level.dispose();
        batch.dispose();
        font.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Tank Game");
        config.setWindowedMode(1280, 1024);
        config.setResizable(false);
        
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
    
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