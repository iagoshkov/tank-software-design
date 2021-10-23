package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.HashSet;

public class RandomLevelGenerator implements LevelGenerator{
    private Player player;
    private ArrayList<Tree> trees = new ArrayList<Tree>();

    @Override
    public Level generateLevel() {
        trees = generateTrees();
        player = generatePlayer();
        return new Level(player, trees);
    }

    private ArrayList<Tree> generateTrees() {
        HashSet<GridPoint2> treesCoordinates = new HashSet<GridPoint2>();
        int numberOfTrees = (int)(Math.random()*20);
        ArrayList<Tree> trees = new ArrayList<Tree>();
        while (treesCoordinates.size() != numberOfTrees) {
            treesCoordinates.add(generateRandomPosition());
        }
        for (GridPoint2 position : treesCoordinates) {
            trees.add(new Tree(position));
        }
        return trees;
    }

    private Player generatePlayer() {
        GridPoint2 position = generateRandomPosition();
        while (trees.contains(position)) {
            position = generateRandomPosition();
        }
        return new Player(position, 0f);
    }

    private GridPoint2 generateRandomPosition() {
        int levelWidth = 10;
        int levelHeight = 8;

        int x = (int)(Math.random()*levelWidth);
        int y = (int)(Math.random()*levelHeight);

        return new GridPoint2(x, y);
    }
}
