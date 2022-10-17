package ru.mipt.bit.platformer;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.GridPoint2;

import java.util.Random;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;

public class PlayersMovementCommand {
    public static GridPoint2 getNewPlayerCoordinates(Input input) {
        GridPoint2 movement = new GridPoint2(0, 0);
        if (input.isKeyPressed(UP) || input.isKeyPressed(W)) {
            movement.y = 1;
        }
        if (input.isKeyPressed(LEFT) || input.isKeyPressed(A)) {
            movement.x = -1;
        }
        if (input.isKeyPressed(DOWN) || input.isKeyPressed(S)) {
            movement.y = -1;
        }
        if (input.isKeyPressed(RIGHT) || input.isKeyPressed(D)) {
            movement.x = 1;
        }
        return movement;
    }

    public static GridPoint2 getNewPlayerCoordinates() {
        Random rd = new Random();
        GridPoint2 movement = new GridPoint2(0, 0);

        if (rd.nextInt(2) == 0) {
            movement.x += rd.nextInt(3) - 1;
        } else {
            movement.y += rd.nextInt(3) - 1;
        }

        return movement;
    }
}
