package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public enum Direction {
    RIGHT(0f, new GridPoint2(1, 0)),
    UP(90f, new GridPoint2(0, 1)),
    LEFT(-180f, new GridPoint2(-1, 0)),
    DOWN(-90f, new GridPoint2(0, -1));

    private final float angle;
    private final GridPoint2 delta;

    Direction(float angle, GridPoint2 delta) {
        this.angle = angle;
        this.delta = delta;
    }

    public float getAngle() {
        return angle;
    }

    public GridPoint2 getDelta() {
        return delta;
    }

    public GridPoint2 calcDestinationCoordinatesFrom(GridPoint2 point) {
        var destinationCoordinate = new GridPoint2(point.x, point.y);
        return destinationCoordinate.add(delta);
    }

    public static Direction random(Random rng) {
        var values = Direction.values();
        return values[rng.nextInt(values.length)];
    }

    public static Direction random() {
        return random(ThreadLocalRandom.current());
    }
}
