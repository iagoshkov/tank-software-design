package ru.mipt.bit.platformer.abstractions;

import com.badlogic.gdx.math.GridPoint2;

public class Tree {
    private final float rotation;
    private final GridPoint2 treeObjectCoordinates;

    public Tree(GridPoint2 treeObjectCoordinates, float rotation) {
        this.rotation = rotation;
        this.treeObjectCoordinates = treeObjectCoordinates;
    }

    public float getRotation() {
        return rotation;
    }

    public GridPoint2 getCoordinates() {
        return treeObjectCoordinates;
    }

}
