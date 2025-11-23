package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;

import main.java.ru.mipt.bit.platformer.collision.CollisionDetector;
import main.java.ru.mipt.bit.platformer.model.Bullet;
import main.java.ru.mipt.bit.platformer.model.MovementStrategy;
import main.java.ru.mipt.bit.platformer.observer.GameLevelObservable;
import main.java.ru.mipt.bit.platformer.util.TileBasedMovement;
import ru.mipt.bit.platformer.Direction;
import ru.mipt.bit.platformer.controller.AITankController;
import ru.mipt.bit.platformer.render.HealthBarDecorator;
import ru.mipt.bit.platformer.render.Renderable;
import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.util.TileMovement;

import org.springframework.beans.factory.annotation.Autowired;

import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank extends GameObject implements Renderable{
    private TextureRegion graphics;
    private float rotation;
    private float movementProgress=1f;
    private final MovementStrategy movement;
    private final CollisionDetector collisionDetector;
    private AITankController aiController;
    private boolean isPlayerControlled;
    private GridPoint2 destinationCoordinates;
    private HealthSystem healthSystem;
    private HealthBarDecorator healthBarDecorator;
    private boolean healthBarVisible = false;
    private Texture bulletTexture;
    private TiledMapTileLayer groundLayer;
    private GameLevelObservable levelObservable;
    private float shootCooldown = 0f;
    private static final float SHOOT_COOLDOWN_TIME = 1f;

    public Tank(Texture texture, TiledMapTileLayer layer, GridPoint2 initialPos,
                MovementStrategy movement, CollisionDetector collisionDetector, 
                HealthSystem healthSystem, Texture bulletTexture, GameLevelObservable levelObservable) {
        
        this.graphics = new TextureRegion(texture);
        this.rectangle = GdxGameUtils.createBoundingRectangle(graphics);
        this.coordinates = new GridPoint2(initialPos);
        this.movement = movement;
        this.collisionDetector = collisionDetector;
        this.rotation = 0f;
        this.destinationCoordinates = new GridPoint2(initialPos);
        this.healthSystem = healthSystem;
        this.healthBarDecorator = new HealthBarDecorator();
        this.bulletTexture = bulletTexture;
        this.groundLayer = layer;
        this.levelObservable = levelObservable;

        GdxGameUtils.moveRectangleAtTileCenter(layer, rectangle, coordinates);
    }
    // Упрощенный конструктор для обратной совместимости
    public Tank(Texture texture, TiledMapTileLayer layer, GridPoint2 initialPos, 
                CollisionDetector collisionDetector, float speed, 
                Texture bulletTexture, GameLevelObservable levelObservable) {
        this(texture, layer, initialPos, 
             new TileBasedMovement(this, new TileMovement(layer, Interpolation.smooth), speed),
             collisionDetector, HealthSystem.createRandomHealth(), bulletTexture, levelObservable);
    }

    // Mетод стрельбы
    public boolean shoot() {
        if (shootCooldown > 0f || !isAlive()) {
            return false;
        }
        // Определяем позицию пули - следующая клетка от танка
        GridPoint2 bulletPosition = getDirection().apply(coordinates);
        // Проверяем, не выходит ли пуля за границы
        if (!isPositionWithinBounds(bulletPosition)) {
            return false;
        }
        
        // Создаем пулю
        Bullet bullet = new Bullet(bulletTexture, groundLayer, bulletPosition, getDirection());
        // Регистрируем пулю в логическом уровне
        levelObservable.addGameObject(bullet);
        // Устанавливакм перезарядку
        shootCooldown = SHOOT_COOLDOWN_TIME;
        return true;
    }

    // Метод для получения текущего направления по rotation
    public Direction getDirection() {
        if (rotation == 90f) return Direction.UP;
        if (rotation == -90f) return Direction.DOWN;
        if (rotation == -180f) return Direction.LEFT;
        return Direction.RIGHT; // rotation == 0f
    }

    public GridPoint2 getDestinationCoordinates() {
        return new GridPoint2(destinationCoordinates);
    }

    @Autowired(required = false)
    public void setAIController(AITankController aiController) {
        this.aiController = aiController;
        this.isPlayerControlled = false;
    }

    public void setPlayerControlled(boolean playerControlled) {
        this.isPlayerControlled = playerControlled;
        if (playerControlled) {
            this.aiController = null;
        }
    }

    public boolean isPlayerControlled() {
        return isPlayerControlled;
    }


    @Override
    public void render(Batch batch) {
        GdxGameUtils.drawTextureRegionUnscaled(batch, graphics, rectangle, rotation);
        
        // Отрисовываем полоску здоровья если включено и здоровье не полное
        if (healthBarVisible && healthSystem.getHealthPercentage() < 1.0f) {
            healthBarDecorator.renderHealthBar(batch, rectangle, healthSystem.getHealthPercentage());
        }
    }
    // Методы для работы со дзоровьем
    public void takeDamage(int damage) {
        healthSystem.takeDamage(damage);
    }
    
    public void heal(int amount) {
        healthSystem.heal(amount);
    }
    
    public boolean isAlive() {
        return healthSystem.isAlive();
    }
    
    public int getCurrentHealth() {
        return healthSystem.getCurrentHealth();
    }
    
    public int getMaxHealth() {
        return healthSystem.getMaxHealth();
    }
    
    public void setHealthBarVisible(boolean visible) {
        this.healthBarVisible = visible;
        this.healthSystem.setShowHealthBar(visible);
    }
    
    public boolean isHealthBarVisible() {
        return healthBarVisible;
    }
    
    @Override
    public void dispose() {
        graphics.getTexture().dispose();
        healthBarDecorator.dispose();
    }

    public boolean move(Direction direction){
        if (movement.isMoving()) return false;
        
        GridPoint2 target = direction.apply(coordinates);
        if (collisionDetector.isPositionBlocked(target)) return false;
        
        movement.moveTo(target);
        this.destinationCoordinates = new GridPoint2(target); 
        rotation = direction.getRotation();
        return true;
    }

    @Override
    public void update(float deltaTime) {
        if (shootCooldown > 0f) {
            shootCooldown -= deltaTime;
        }
        
        if (aiController != null && !isPlayerControlled) {
            aiController.update(deltaTime);
        }
        movement.update(deltaTime);
    }

    private boolean isPositionWithinBounds(GridPoint2 position) {
        return position.x >= 0 && position.y >= 0 && 
               position.x < groundLayer.getWidth() && 
               position.y < groundLayer.getHeight();
    }
    
    public boolean canShoot() {
        return shootCooldown <= 0f && isAlive();
    }

    //метод для получения направлени движения
    public Direction getMovementDirection() {
        if (!isMoving()) {
            return null;
        }
        
        int dx = destinationCoordinates.x - coordinates.x;
        int dy = destinationCoordinates.y - coordinates.y;
        
        if (dx > 0) return Direction.RIGHT;
        if (dx < 0) return Direction.LEFT;
        if (dy > 0) return Direction.UP;
        if (dy < 0) return Direction.DOWN;
        
        return null;
    }
    
    public boolean isMoving() {
        return movement.isMoving();
    }
    
     public float getMovementProgress() {
            return movement.isMoving() ? 0.5f : 1f; 
    }
    
     public void setMovementStrategy(MovementStrategy movement) {
        this.movement = movement; 
    }
}