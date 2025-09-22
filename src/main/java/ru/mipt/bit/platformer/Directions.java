package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public enum Directions {
    UP(0, 1, 90f, Input.Keys.UP, Input.Keys.W),
    LEFT(-1, 0, -180f, Input.Keys.LEFT, Input.Keys.A),
    DOWN(0, -1, -90f, Input.Keys.DOWN, Input.Keys.S),
    RIGHT(1, 0, 0f, Input.Keys.RIGHT, Input.Keys.D);

    public final int dx;
    public final int dy;
    public final float rotation;
    public final int key1;
    public final int key2;

    Directions(int dx, int dy, float rotation, int key1, int key2) {
        this.dx = dx;
        this.dy = dy;
        this.rotation = rotation;
        this.key1 = key1;
        this.key2 = key2;
    }

    public boolean isPressed() {
        return Gdx.input.isKeyPressed(key1) || Gdx.input.isKeyPressed(key2);
    }
}
