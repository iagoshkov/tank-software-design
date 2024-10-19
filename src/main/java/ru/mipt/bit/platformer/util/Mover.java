package ru.mipt.bit.platformer.util;

import ru.mipt.bit.platformer.objects.Movable;

import java.util.Collection;

public class Mover {

    private final TileMovement tileMovement;

    public Mover(TileMovement tileMovement) {
        this.tileMovement = tileMovement;
    }

    public static void move(float deltaTime, final Collection<Movable> movables) {
        for (Movable movable : movables) {
            movable.changeMovementState(deltaTime);
        }
    }

    public TileMovement getTileMovement() {
        return tileMovement;
    }
}
