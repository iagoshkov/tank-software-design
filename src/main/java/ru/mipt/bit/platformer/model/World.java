package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import java.util.Set;

public class World {
    private final Player player;
    private final Set<GridPoint2> obstacles;
    private final TileGrid tileGrid;

    public World(Player player, Set<GridPoint2> obstacles, TileGrid tileGrid) {
        this.player = player;
        this.obstacles = obstacles;
        this.tileGrid = tileGrid;
    }

    public Player getPlayer() {
        return player;
    }

    public Set<GridPoint2> getObstacles() {
        return obstacles;
    }

    public TileGrid getTileGrid() {
        return tileGrid;
    }
}