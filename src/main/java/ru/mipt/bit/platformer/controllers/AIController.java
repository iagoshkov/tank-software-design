package ru.mipt.bit.platformer.controllers;

import ru.mipt.bit.platformer.util.Direction;

import java.util.Random;

public class AIController implements InputController {
    private final Random random;
    private Direction lastDirection = Direction.NULL;
    private float shootCooldown = 0f;

    public AIController() {
        this.random = new Random();
    }

    @Override
    public Direction getInputDirection() {
        // 30% chance to change direction
        if (lastDirection == Direction.NULL || random.nextFloat() < 0.3f) {
            Direction[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
            lastDirection = directions[random.nextInt(directions.length)];
        }
        return lastDirection;
    }

    @Override
    public boolean isShootPressed() {
        // Для AI используем отдельную логику
        return shouldShoot();
    }

    @Override
    public boolean shouldShoot() {
        // 15% chance to shoot каждый тик + кд
        if (shootCooldown > 0) {
            shootCooldown -= 0.1f;
            return false;
        }
        
        if (random.nextFloat() < 0.15f) {
            shootCooldown = 1f; // 1 секунда кд
            return true;
        }
        return false;
    }
}