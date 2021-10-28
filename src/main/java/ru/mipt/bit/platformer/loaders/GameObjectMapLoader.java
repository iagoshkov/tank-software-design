package ru.mipt.bit.platformer.loaders;

import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

public interface GameObjectMapLoader {
    void loadMap() throws MapLoadingException;

    GridPoint2 getPlayerPosition();

    List<GridPoint2> getBotPositions();

    List<GridPoint2> getTreePositions();
}
