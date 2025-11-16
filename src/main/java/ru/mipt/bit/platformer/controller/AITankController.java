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
    private ShootCommand shootCommand;
    private static final float SHOOT_PROBABILITY = 0.3f;

    public AITankController(Tank tank) {
        this.tank = tank;
        this.random = new Random();
        this.currentDirection = getRandomDirection();
        this.shootCommand = new ShootCommand(tank);
    }

    public void update(float deltaTime) {
        timeSinceLastDirectionChange += deltaTime;
        
        if (timeSinceLastDirectionChange >= DIRECTION_CHANGE_INTERVAL) {
            // случйно выбираем - двигаться или стрелять
            if (random.nextFloat() < SHOOT_PROBABILITY && tank.canShoot()) {
                shootCommand.executeAI();
            } else {
                currentDirection = getRandomDirection();
            }
            timeSinceLastDirectionChange = 0f;
        }
        
        if (!tank.isMoving() && random.nextFloat() < 0.1f) {
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