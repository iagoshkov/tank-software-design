package ru.mipt.bit.platformer.control.commands;

import ru.mipt.bit.platformer.driver.LogicLevel;
import ru.mipt.bit.platformer.objects.gameObjects.Tank;

public class MoveRightCommand implements Command {

    private final Tank tank;

    public MoveRightCommand(Tank tank){
        this.tank = tank;
    }

    @Override
    public void execute(){
        tank.moveRight();
    }
}
