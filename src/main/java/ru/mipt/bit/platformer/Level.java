package ru.mipt.bit.platformer;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Level {
    private Player player;
    private ArrayList<Tree> treeObstacles;

    private ArrayList<Player> otherTanks;

    Level(Player player, ArrayList<Tree> trees) {
        this(player, trees, null);
    }

    Level(Player player, ArrayList<Tree> trees, ArrayList<Player> otherTanks) {
        this.player = player;
        this.treeObstacles = trees;
        this.otherTanks = otherTanks;
    }

    public Player getPlayer() { return player; }

    public ArrayList<Tree> getTreeObstacles() { return treeObstacles; }

    public ArrayList<Player> getOtherTanks() {
        return otherTanks;
    }
}
