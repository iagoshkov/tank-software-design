package ru.mipt.bit.platformer.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.enums.Direction;
import ru.mipt.bit.platformer.entity.Tank;
import ru.mipt.bit.platformer.entity.Tree;

import static com.badlogic.gdx.Input.Keys.*;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class MovementService {

    public void processMoving(Tank tank, Tree tree) {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            move(tank, tree, Direction.UP);
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            move(tank, tree, Direction.RIGHT);
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            move(tank, tree, Direction.LEFT);
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            move(tank, tree, Direction.DOWN);
        }
    }

    private void move(Tank tank, Tree tree, Direction dir) {
        if (isEqual(tank.getMovementProgress(), 1f)){
            if (!tree.getCoordinates().equals(getPredictedPoint(tank.getCoordinates(), dir))) {
                tank.setCoordinatesDestination(tank.getCoordinatesDestination().add(dir.dx, dir.dy));
                tank.setMovementProgress(0f);
            }
            tank.setRotation(dir.rotation);
        }
    }

    private GridPoint2 getPredictedPoint(GridPoint2 point, Direction dir) {
        return new GridPoint2(point).add(dir.dx, dir.dy);
    }
}

