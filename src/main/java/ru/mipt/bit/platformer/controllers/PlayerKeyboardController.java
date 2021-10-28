package ru.mipt.bit.platformer.controllers;

import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.input.KeyboardListener;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;

public class PlayerKeyboardController {
    private final Tank player;

    public PlayerKeyboardController(Tank player) {
        this.player = player;
    }

    private static Direction calcDirectionFromInputKey(Integer key) {
        if (key == null) {
            return null;
        }
        if (key == Input.Keys.RIGHT || key == Input.Keys.D) {
            return Direction.RIGHT;
        }
        if (key == Input.Keys.UP || key == Input.Keys.W) {
            return Direction.UP;
        }
        if (key == Input.Keys.LEFT || key == Input.Keys.A) {
            return Direction.LEFT;
        }
        if (key == Input.Keys.DOWN || key == Input.Keys.S) {
            return Direction.DOWN;
        }
        throw new IllegalArgumentException();
    }

    public void process(float deltaTime) {
        Integer key = KeyboardListener.getPressedKey();
        Direction direction = calcDirectionFromInputKey(key);
        if (direction != null) {
            player.tryRotateAndStartMovement(calcDirectionFromInputKey(key));
        }
        player.makeProgress(deltaTime);
    }
}
