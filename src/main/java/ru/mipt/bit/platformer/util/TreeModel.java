package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

public class TreeModel extends GameObjectModel {

    public TreeModel(GridPoint2 coordinates) {
        super(coordinates);
        this.rotation = 0f;
    }

    @Override
    public void update(float deltaTime) {
        // Trees are static - no updates
    }

    public boolean occupiesPosition(GridPoint2 position) {
        return coordinates.equals(position);
    }
}