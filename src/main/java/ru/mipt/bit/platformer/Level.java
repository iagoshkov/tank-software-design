package ru.mipt.bit.platformer;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;

public class Level implements Disposable {
    private TiledMap map;
    private Player player;
    private ArrayList<Tree> treeObstacles;

    Level(Player player, ArrayList<Tree> trees) {
        map = new TmxMapLoader().load("level.tmx");
        this.player = player;
        this.treeObstacles = trees;
    }

    @Override
    public void dispose() {
        map.dispose();
    }

    public TiledMap getMap() { return map; }

    public Player getPlayer() { return player; }

    public ArrayList<Tree> getTreeObstacles() { return treeObstacles; }
}
