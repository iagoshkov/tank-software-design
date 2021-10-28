package ru.mipt.bit.platformer.ai;

import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;

public class MoveCommand implements Command {
    private final Tank tank;
    private final Direction direction;

    public MoveCommand(Tank tank, Direction direction) {
        this.tank = tank;
        this.direction = direction;
    }

    @Override
    public void execute() {
        tank.tryRotateAndStartMovement(direction);
    }
}
