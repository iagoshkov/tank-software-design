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
import ru.mipt.bit.platformer.util.TileMovement;


public abstract class GameObject{
    protected Rectangle rectangle;
    protected GridPoint2 coordinates;

    public abstract void update(float deltaTimt);

     public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates);
    }
    
    public Rectangle getRectangle() {
        return new Rectangle(rectangle);
    }

    // Проверка коллизий
     public boolean collidesWith(GameObject other) {
        return this.rectangle.overlaps(other.getRectangle());
    }

    // Получние границ
     public Rectangle getBounds() {
        return new Rectangle(rectangle);
    }
}