package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.render.Renderable;
import ru.mipt.bit.platformer.util.GdxGameUtils;

public class Bullet extends GameObject implements Renderable {
    private TextureRegion graphics;
    private Direction direction;
    private float speed = 0.2f; // скорость движения пули
    private int damage = 25;
    private boolean active = true;
    
    public Bullet(Texture texture, TiledMapTileLayer layer, GridPoint2 position, Direction direction) {
        this.graphics = new TextureRegion(texture);
        this.rectangle = GdxGameUtils.createBoundingRectangle(graphics);
        this.coordinates = new GridPoint2(position);
        this.direction = direction;
        
        GdxGameUtils.moveRectangleAtTileCenter(layer, rectangle, coordinates);
    }
    
    @Override
    public void render(Batch batch) {
        if (active) {
            GdxGameUtils.drawTextureRegionUnscaled(batch, graphics, rectangle, direction.getRotation());
        }
    }
    
    @Override
    public void dispose() {
        graphics.getTexture().dispose();
    }
    
    @Override
    public void update(float deltaTime) {
        if (active) {
            // Двигаем пулю в направлении выстрела
            GridPoint2 newPosition = direction.apply(coordinates);
            coordinates.set(newPosition);
            GdxGameUtils.moveRectangleAtTileCenter(getTileLayer(), rectangle, coordinates);
        }
    }
    
    public void onCollision() {
        active = false;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public int getDamage() {
        return damage;
    }
    
    public Direction getDirection() {
        return direction;
    }
    
    private TiledMapTileLayer getTileLayer() {
        return null;
    }
}