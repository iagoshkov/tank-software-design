package ru.mipt.bit.platformer.controller;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;

import java.util.Random;

public class AITankController {
    private final Tank tank;
    private final Random random;
    private Direction currentDirection;
    private float timeSinceLastDirectionChange = 0f;
    private static final float DIRECTION_CHANGE_INTERVAL = 1f;

    public AITankController(Tank tank) {
        this.tank = tank;
        this.random = new Random();
        this.currentDirection = getRandomDirection();
    }

    public void update(float deltaTime) {
        timeSinceLastDirectionChange += deltaTime;
        
        if (timeSinceLastDirectionChange >= DIRECTION_CHANGE_INTERVAL) {
            currentDirection = getRandomDirection();
            timeSinceLastDirectionChange = 0f;
        }
        
        if (!tank.isMoving()) {
            tank.move(currentDirection);
        }
    }
    
    private Direction getRandomDirection() {
        Direction[] directions = Direction.values();
        return directions[random.nextInt(directions.length)];
    }
    
    public Direction getCurrentDirection() {
        return currentDirection;
    }
}