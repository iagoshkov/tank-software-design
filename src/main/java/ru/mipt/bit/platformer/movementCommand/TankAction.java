package ru.mipt.bit.platformer.movementCommand;

import com.badlogic.gdx.math.GridPoint2;

public enum TankAction {
    WAIT(),
    MOVE_UP(new GridPoint2(0, 1)),
    MOVE_DOWN(new GridPoint2(0, -1)),
    MOVE_RIGHT(new GridPoint2(1, 0)),
    MOVE_LEFT(new GridPoint2(-1, 0)),
    SHOOT;

    public GridPoint2 getMovement() {
        return movement;
    }

    private GridPoint2 movement;
    TankAction() {}
    TankAction(GridPoint2 movement) {
        this.movement = movement;
    }
}
