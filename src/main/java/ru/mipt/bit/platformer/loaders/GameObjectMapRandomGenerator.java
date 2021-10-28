package ru.mipt.bit.platformer.loaders;

import com.badlogic.gdx.math.GridPoint2;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GameObjectMapRandomGenerator implements GameObjectMapLoader {
    private final int height;
    private final int width;
    private final int nTrees;
    private final int nBots;

    private GridPoint2 playerPosition;
    private Set<GridPoint2> botPositions;
    private Set<GridPoint2> treePositions;

    public GameObjectMapRandomGenerator(int height, int width, int nTrees, int nBots) {
        if (height * width < nTrees + nBots + 1) {
            throw new IllegalArgumentException("too many map objects");
        }

        this.height = height;
        this.width = width;
        this.nTrees = nTrees;
        this.nBots = nBots;
    }

    private void clear() {
        playerPosition = null;
        botPositions = new HashSet<>();
        treePositions = new HashSet<>();
    }

    private boolean isOccupied(GridPoint2 point) {
        return point.equals(playerPosition) || botPositions.contains(point) || treePositions.contains(point);
    }

    private GridPoint2 generateRandomEmptyPosition() {
        var point = new GridPoint2();
        do {
            point.x = ThreadLocalRandom.current().nextInt(width);
            point.y = ThreadLocalRandom.current().nextInt(height);
        } while (isOccupied(point));
        return point;
    }

    private void generatePlayerPosition() {
        playerPosition = generateRandomEmptyPosition();
    }

    private void generateTreePositions() {
        for (int i = 0; i < nTrees; ++i) {
            treePositions.add(generateRandomEmptyPosition());
        }
    }

    private void generateBotPositions() {
        for (int i = 0; i < nBots; ++i) {
            botPositions.add(generateRandomEmptyPosition());
        }
    }

    @Override
    public void loadMap() {
        clear();
        generatePlayerPosition();
        generateBotPositions();
        generateTreePositions();
    }

    @Override
    public GridPoint2 getPlayerPosition() {
        return playerPosition;
    }

    @Override
    public List<GridPoint2> getBotPositions() {
        return new ArrayList<>(botPositions);
    }

    @Override
    public List<GridPoint2> getTreePositions() {
        return new ArrayList<>(treePositions);
    }
}
