package ru.mipt.bit.platformer.commands;

import ru.mipt.bit.platformer.objects.Tank;

public class ShootCommand implements Command {
    private final Tank tank;
    private boolean executed = false;

    public ShootCommand(Tank tank) {
        this.tank = tank;
    }

    @Override
    public void execute() {
        if (!executed) {
            tank.shoot();
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