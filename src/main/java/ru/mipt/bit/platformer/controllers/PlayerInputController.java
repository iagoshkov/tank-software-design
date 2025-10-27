package ru.mipt.bit.platformer.controllers;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.util.Direction;

import static com.badlogic.gdx.Input.Keys.*;

public class PlayerInputController implements InputController {
    
    @Override
    public Direction getInputDirection() {
        if (isKeyPressed(UP, W)) {
            return Direction.UP;
        } else if (isKeyPressed(LEFT, A)) {
            return Direction.LEFT;
        } else if (isKeyPressed(DOWN, S)) {
            return Direction.DOWN;
        } else if (isKeyPressed(RIGHT, D)) {
            return Direction.RIGHT;
        }
        return Direction.NULL;
    }

    @Override
    public boolean isShootPressed() {
        return isKeyPressed(SPACE);
    }

    private boolean isKeyPressed(int... keyCodes) {
        for (int keyCode : keyCodes) {
            if (Gdx.input.isKeyPressed(keyCode)) {
                return true;
            }
        }
        return false;
    }
}