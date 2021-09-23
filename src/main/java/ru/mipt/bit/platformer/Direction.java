package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.A;

public class Direction {
    private GridPoint2 movementVector;
    private float rotation;

    Direction(String direction){
        if (direction.equals("UP")) {
            movementVector = new GridPoint2(0, 1);
            rotation = 90f;
        }
        if (direction.equals("LEFT")) {
            movementVector = new GridPoint2(-1, 0);
            rotation = -180f;
        }
        if (direction.equals("DOWN")) {
            movementVector = new GridPoint2(0, -1);
            rotation = -90f;
        }
        if (direction.equals("RIGHT")) {
            movementVector = new GridPoint2(1, 0);
            rotation = 0f;
        }
    }

    public GridPoint2 getMovementVector() {
        return movementVector;
    }

    public float getRotation() {
        return rotation;
    }
}
