package ru.mipt.bit.platformer.loaders;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class GameObjectMapRandomGenerator implements GameObjectMapLoader {
    private final int height;
    private final int width;
    private final int nTrees;

    private GridPoint2 playerPosition;
    private Set<GridPoint2> treePositions;

    public GameObjectMapRandomGenerator(int height, int width, int nTrees) {
        this.height = height;
        this.width = width;
        this.nTrees = nTrees;
    }

    private void clear() {
        playerPosition = null;
        treePositions = new HashSet<>();
    }

    private boolean isOccupied(GridPoint2 point) {
        return point.equals(playerPosition) || treePositions.contains(point);
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

    @Override
    public void loadMap() {
        clear();
        generatePlayerPosition();
        generateTreePositions();
    }

    @Override
    public GridPoint2 getPlayerPosition() {
        return playerPosition;
    }

    @Override
    public List<GridPoint2> getTreePositions() {
        return new ArrayList<>(treePositions);
    }
}
