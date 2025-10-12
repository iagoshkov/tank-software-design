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
import main.java.ru.mipt.bit.platformer.model.MovementStrategy;
import main.java.ru.mipt.bit.platformer.util.TileBasedMovement;
import ru.mipt.bit.platformer.Direction;
import ru.mipt.bit.platformer.render.Renderable;
import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank extends GameObject implements Renderable{
    private TextureRegion graphics;
    private float rotation;
    private float movementProgress=1f;
    private final MovementStrategy movement;
    private final CollisionDetector collisionDetector;

    public Tank(Texture texture, TiledMapTileLayer layer, GridPoint2 initialPos,
        MovementStrategy movement, CollisionDetector collisionDetector){
        
        this.graphics = new TextureRegion(texture);
        this.rectangle = GdxGameUtils.createBoundingRectangle(graphics);
        this.coordinates = new GridPoint2(initialPos);
        this.movement = movement;
        this.collisionDetector = collisionDetector;
        this.rotation = 0f;
        
        GdxGameUtils.moveRectangleAtTileCenter(layer, rectangle, coordinates);
    }
    // Упрощенный конструктор для обратной совместимости
    public Tank(Texture texture, TiledMapTileLayer layer, GridPoint2 initialPos, 
                CollisionDetector collisionDetector, float speed) {
        this(texture, layer, initialPos, 
             new TileBasedMovement(this, new TileMovement(layer, Interpolation.smooth), speed),
             collisionDetector);
    }

    @Override
    public void render(Batch batch) {
        GdxGameUtils.drawTextureRegionUnscaled(batch, graphics, rectangle, rotation);
    }
    
    @Override
    public void dispose() {
        graphics.getTexture().dispose();
    }
    public boolean move(Direction direction){
        if (movement.isMoving()) return false;
        
        GridPoint2 target = direction.apply(coordinates);
        if (collisionDetector.isPositionBlocked(target)) return false;
        
        movement.moveTo(target);
        rotation = direction.getRotation();
        return true;
    }

    public void update(float deltaTime) {
        movement.update(deltaTime);
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