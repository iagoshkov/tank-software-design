package ru.mipt.bit.platformer.control;

import ru.mipt.bit.platformer.control.commands.Command;

public class Control {
    private final Command moveUpCommand;
    private final Command moveDownCommand;
    private final Command moveLeftCommand;
    private final Command moveRightCommand;
    private final Command shootCommand;

    public Control(Command moveUpCommand,
                           Command moveDownCommand,
                           Command moveLeftCommand,
                           Command moveRightCommand,
                   Command shootCommand){
        this.moveUpCommand = moveUpCommand;
        this.moveDownCommand = moveDownCommand;
        this.moveLeftCommand = moveLeftCommand;
        this.moveRightCommand = moveRightCommand;
        this.shootCommand = shootCommand;
    }

    public void moveUp(){
        moveUpCommand.execute();
    }

    public void moveDown(){
        moveDownCommand.execute();
    }

    public void moveLeft(){
        moveLeftCommand.execute();
    }

    public void moveRight(){
        moveRightCommand.execute();
    }

    public void shoot(){ shootCommand.execute(); }
}
