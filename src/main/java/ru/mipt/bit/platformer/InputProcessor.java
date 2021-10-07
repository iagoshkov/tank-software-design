package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.List;

public class InputProcessor {
    private static final List<Integer> ACCEPTABLE_KEYS = List.of(
            Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.LEFT, Input.Keys.DOWN, Input.Keys.W, Input.Keys.A, Input.Keys.S, Input.Keys.D);

    public static Integer getPressedKey() {
        for (Integer key : ACCEPTABLE_KEYS) {
            if (Gdx.input.isKeyPressed(key)) {
                return key;
            }
        }
        return null;
    }
}
