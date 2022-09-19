package ru.mipt.bit.platformer.util;

public enum Rotation {
    UP(90f),
    DOWN(-90f),
    LEFT(-180f),
    RIGHT(0f),
    ;

    private final float angle;
    Rotation(float angle) {
        this.angle = angle;
    }
    public float toFloat() {
        return this.angle;
    }
}
