package ru.mipt.bit.platformer.control.managers;

import ru.mipt.bit.platformer.control.Control;
import ru.mipt.bit.platformer.control.commands.Command;

public class ControlByRandom implements Manager {
    private final Control control;

    public ControlByRandom(Command moveUpCommand,
                   Command moveDownCommand,
                   Command moveLeftCommand,
                   Command moveRightCommand,
                           Command shootCommand){
        control = new Control(moveUpCommand, moveDownCommand, moveLeftCommand, moveRightCommand, shootCommand);
    }

    @Override
    public void executeCommand(){
        int c = (int) (Math.random() * 4);
        int shooter = (int) (Math.random() * 100);
        if (shooter < 10) {
            control.shoot();
        }
        else {
            switch (c) {
                case 0:
                    control.moveUp();
                    break;
                case 1:
                    control.moveDown();
                    break;
                case 2:
                    control.moveLeft();
                    break;
                case 3:
                    control.moveRight();
                    break;
            }
        }
    }
}
