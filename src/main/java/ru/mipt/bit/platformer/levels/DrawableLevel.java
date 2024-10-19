package ru.mipt.bit.platformer.levels;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public interface DrawableLevel extends Level {
    void dispose();

    void render();

    TiledMapTileLayer getGroundLayer();
}
