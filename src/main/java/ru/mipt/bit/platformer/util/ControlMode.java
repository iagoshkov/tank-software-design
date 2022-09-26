package ru.mipt.bit.platformer.util;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.S;

public class ControlMode {
    private final HashMap<Integer, Orientation> keyboardControl = initKeyboardControl();

    private HashMap<Integer, Orientation> initKeyboardControl() {
        HashMap<Integer, Orientation> control = new HashMap<Integer, Orientation>();

        control.put(LEFT, Orientation.LEFT);
        control.put(A, Orientation.LEFT);
        control.put(RIGHT, Orientation.RIGHT);
        control.put(D, Orientation.RIGHT);
        control.put(UP, Orientation.UP);
        control.put(W, Orientation.UP);
        control.put(DOWN, Orientation.DOWN);
        control.put(S, Orientation.DOWN);

        return control;

    }

    public ControlMode() {
    }
}
