package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import java.util.List;

public class Tree extends RigidBody {

    public Tree(Texture texture, GridPoint2 coordinates) {
        super(texture, coordinates);
    }

    @Override
    public void update(float deltaTime, List<GridPoint2> obstacleCoordinates) {
    }

    @Override
    public void render(Batch batch) {
        super.render(batch);
    }
}
