// ru/mipt/bit/platformer/commands/MoveCommand.java
package ru.mipt.bit.platformer.commands;

import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.util.Direction;

public class MoveCommand implements Command {
    private final Tank tank;
    private final Direction direction;
    private boolean executed = false;

    public MoveCommand(Tank tank, Direction direction) {
        this.tank = tank;
        this.direction = direction;
    }

    @Override
    public void execute() {
        if (!executed) {
            tank.move(direction);
            executed = true;
        }
    }

    @Override
    public void undo() {
        if (executed) {
            executed = false;
        }
    }
}