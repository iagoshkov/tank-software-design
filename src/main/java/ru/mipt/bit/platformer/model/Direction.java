package ru.mipt.bit.platformer;

import java.util.function.Function;

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

import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.function.Function;

import static com.badlogic.gdx.Input.Keys.*;

public enum Direction {
    UP(90f, p -> GdxGameUtils.incrementedY(p), UP, W),
    DOWN(-90f, p -> GdxGameUtils.decrementedY(p), DOWN, S),
    LEFT(-180f, p -> GdxGameUtils.decrementedX(p), LEFT, A),
    RIGHT(0f, p -> GdxGameUtils.incrementedX(p), RIGHT, D);
    
    private final float rotation;
    private final Function<GridPoint2, GridPoint2> transformer;
    private final int[] keyCodes;
    
    Direction(float rotation, Function<GridPoint2, GridPoint2> transformer, int... keyCodes) {
        this.rotation = rotation;
        this.transformer = transformer;
        this.keyCodes = keyCodes;
    }
    
    public GridPoint2 apply(GridPoint2 point) {
        return transformer.apply(point);
    }
    
    public float getRotation() {
        return rotation;
    }

    public int[] getKeyCodes() {
        return keyCodes;
    }

    public boolean isPressed() {
        for (int keyCode : keyCodes) {
            if (com.badlogic.gdx.Gdx.input.isKeyPressed(keyCode)) {
                return true;
            }
        }
        return false;
    }

     public GridPoint2 getDirectionVector() {
        switch (this) {
            case UP: return new GridPoint2(0, 1);
            case DOWN: return new GridPoint2(0, -1);
            case LEFT: return new GridPoint2(-1, 0);
            case RIGHT: return new GridPoint2(1, 0);
            default: return new GridPoint2(0, 0);
        }
    }
}