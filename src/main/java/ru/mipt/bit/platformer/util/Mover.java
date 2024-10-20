package ru.mipt.bit.platformer.util;

import ru.mipt.bit.platformer.keys.Direction;
import ru.mipt.bit.platformer.levels.Level;
import ru.mipt.bit.platformer.objects.AI;
import ru.mipt.bit.platformer.objects.GameObject;
import ru.mipt.bit.platformer.objects.Movable;

import java.util.Collection;

public class Mover {

    private final TileMovement tileMovement;

    public Mover(TileMovement tileMovement) {
        this.tileMovement = tileMovement;
    }

    public static void move(float deltaTime,
                            Collection<Movable> movables,
                            Collection<? extends GameObject> obstacles,
                            Level level) {
        for (Movable movable : movables) {
            if (movable instanceof AI ai) {
                Direction direction = ai.generateDirection();
                if (movable.canMoveToDirection(direction, obstacles, level)) {
                    movable.move(direction);
                }
            }
            movable.changeMovementState(deltaTime);
        }
    }

    public TileMovement getTileMovement() {
        return tileMovement;
    }
}
