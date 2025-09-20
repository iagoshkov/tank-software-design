package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

public class Tree extends GameObject {

    public Tree(GridPoint2 coordinates, TextureRegion graphics, TiledMapTileLayer groundLayer) {
        super(coordinates, graphics, groundLayer);
        this.rotation = 0f;
    }

    @Override
    public void update(float deltaTime) {
        // Деревья статичны - без обновлений
    }

    public boolean occupiesPosition(GridPoint2 position) {
        return coordinates.equals(position);
    }
}