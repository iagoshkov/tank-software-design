package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

public class InputHandler {
    private final TankModel tank;
    private final GameField gameField;

    public InputHandler(TankModel tank, GameField gameField) {
        this.tank = tank;
        this.gameField = gameField;
    }

    public void handleInput(float deltaTime) {
        // Проверяю все направления на входе
        for (Direction direction : Direction.values()) {
            if (direction.isKeyPressed() && tank.isMovementComplete()) {
                GridPoint2 newPosition = direction.calculateNewPosition(tank.getCoordinates());

                // Проверка коллизий
                if (!gameField.isPositionOccupied(newPosition)) {
                    tank.moveTo(newPosition, direction);
                }
            }
        }

        // TODO: Add shooting handler for space key
    }
}