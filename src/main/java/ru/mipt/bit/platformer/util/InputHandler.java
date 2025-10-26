package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

public class InputHandler {
    private final Movable movable;
    private final Field field;

    public InputHandler(Movable movable, Field field) {
        this.movable = movable;
        this.field = field;
    }

    public void handleInput(float deltaTime) {
        // Проверяю все направления на входе
        for (Direction direction : Direction.values()) {
            if (direction.isKeyPressed() && movable.isMovementComplete()) {
                GridPoint2 newPosition = direction.calculateNewPosition(movable.getCoordinates());

                // Проверка коллизий
                if (!field.isPositionOccupied(newPosition)) {
                    movable.moveTo(newPosition, direction);
                }
            }
        }

        // TODO: Add shooting handler for space key
    }
}