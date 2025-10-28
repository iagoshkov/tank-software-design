package ru.mipt.bit.platformer.models;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.graphics.Renderable;
import ru.mipt.bit.platformer.util.TileMovement;

public class Tree extends RigidBody implements Renderable {

    private final Rectangle rectangle;

    public Tree(TileMovement tileMovement, GridPoint2 coordinates) {
        super(coordinates);
        this.rectangle = new Rectangle();
        tileMovement.moveRectangleToTileCenter(rectangle, coordinates);
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public float getRotation() {
        return 0f;
    }
}
