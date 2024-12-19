package ru.mipt.bit.platformer.entity.playerinput;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.entity.tank.Direction;

import static com.badlogic.gdx.Input.Keys.*;

public class PlayerInput {
        public static Direction chooseDirection() {
            if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
                return Direction.RIGHT;
            } else if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
                return Direction.LEFT;
            } else if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
                return Direction.UP;
            } else if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
                return Direction.DOWN;
            }
            return Direction.NULL;
        }
    }
