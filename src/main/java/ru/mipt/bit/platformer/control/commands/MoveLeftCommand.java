package ru.mipt.bit.platformer.control.commands;

import ru.mipt.bit.platformer.driver.LogicLevel;
import ru.mipt.bit.platformer.objects.gameObjects.Tank;

public class MoveLeftCommand implements Command {

    private final Tank tank;

    public MoveLeftCommand(Tank tank){
        this.tank = tank;
    }

    @Override
    public void execute(){
        tank.moveLeft();
    }
}
