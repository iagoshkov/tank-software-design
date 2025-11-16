package ru.mipt.bit.platformer.controllers;

import ru.mipt.bit.platformer.util.Direction;

import java.util.Random;

public class AiInputController implements InputController {
    private final Random random;
    private Direction lastDirection = Direction.NULL;
    private int sameDirectionCount = 0;
    private static final int MAX_SAME_DIRECTION = 3;
    private static final float SHOOT_PROBABILITY = 0.8f;

    public AiInputController() {
        this.random = new Random();
    }

    public AiInputController(Random random) {
        this.random = random;
    }

    @Override
    public Direction getInputDirection() {
        if (lastDirection != Direction.NULL && 
            sameDirectionCount < MAX_SAME_DIRECTION && 
            random.nextFloat() > 0.3f) {
            sameDirectionCount++;
            return lastDirection;
        }

        Direction[] directions = Direction.values();
        lastDirection = directions[random.nextInt(directions.length - 1)];
        sameDirectionCount = 1;
        return lastDirection;
    }

    @Override
    public boolean isShootPressed() {
        return random.nextFloat() < SHOOT_PROBABILITY;
    }

    @Override
    public boolean shouldShoot() {
        return isShootPressed();
    }
}