package ru.mipt.bit.platformer;

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

public enum Direction {
    UP(90f, p -> GdxGameUtils.incrementedY(p)),
    DOWN(-90f, p -> GdxGameUtils.decrementedY(p)),
    LEFT(-180f, p -> GdxGameUtils.decrementedX(p)),
    RIGHT(0f, p -> GdxGameUtils.incrementedX(p));
    
    private final float rotation;
    private final Function<GridPoint2, GridPoint2> transformer;
    
    Direction(float rotation, Function<GridPoint2, GridPoint2> transformer) {
        this.rotation = rotation;
        this.transformer = transformer;
    }
    
    public GridPoint2 apply(GridPoint2 point) {
        return transformer.apply(point);
    }
    
    public float getRotation() {
        return rotation;
    }
}