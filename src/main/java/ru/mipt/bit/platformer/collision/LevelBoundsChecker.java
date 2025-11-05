package ru.mipt.bit.platformer.collision;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class LevelBoundsChecker {
    private final TiledMapTileLayer groundLayer;
    
    public LevelBoundsChecker(TiledMapTileLayer groundLayer) {
        this.groundLayer = groundLayer;
    }
    
    public boolean isWithinBounds(GridPoint2 position) {
        return position.x >= 0 && 
               position.y >= 0 && 
               position.x < groundLayer.getWidth() && 
               position.y < groundLayer.getHeight();
    }
    
    public int getLevelWidth() {
        return groundLayer.getWidth();
    }
    
    public int getLevelHeight() {
        return groundLayer.getHeight();
    }
}