package ru.mipt.bit.platformer.controllers;

import ru.mipt.bit.platformer.util.Direction;

import java.util.Random;

public class AiInputController implements InputController {
    private final Random random;
    private Direction lastDirection = Direction.NULL;
    private int sameDirectionCount = 0;
    private static final int MAX_SAME_DIRECTION = 3;

    // Конструктор без параметров
    public AiInputController() {
        this.random = new Random();
    }

    // Конструктор с параметрами (если нужен для других случаев)
    public AiInputController(Random random) {
        this.random = random;
    }

    @Override
    public Direction getInputDirection() {
        // 30% вероятность сменить направление даже если можно продолжать в том же
        if (lastDirection != Direction.NULL && 
            sameDirectionCount < MAX_SAME_DIRECTION && 
            random.nextFloat() > 0.3f) {
            sameDirectionCount++;
            return lastDirection;
        }

        // Выбираем случайное направление
        Direction[] directions = Direction.values();
        lastDirection = directions[random.nextInt(directions.length - 1)]; // -1 чтобы исключить NULL
        sameDirectionCount = 1;
        return lastDirection;
    }

    @Override
    public boolean isShootPressed() {
        return random.nextFloat() < 0.05f;
    }
}