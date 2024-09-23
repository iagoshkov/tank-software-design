package ru.mipt.bit.platformer.core;

import com.badlogic.gdx.math.GridPoint2;

public class Tree {
    private final GridPoint2 treeObstacleCoordinates;
    private final float rotation;

    public Tree(GridPoint2 treeObstacleCoordinates, float rotation) {
        this.treeObstacleCoordinates = treeObstacleCoordinates;
        this.rotation = rotation;
    }

    public GridPoint2 getCoordinates() {
        return treeObstacleCoordinates;
    }

    public float getRotation() {
        return rotation;
    }
}
