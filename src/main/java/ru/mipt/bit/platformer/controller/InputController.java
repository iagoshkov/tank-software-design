package ru.mipt.bit.platformer.controller;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;


public class InputController {
    private final Tank playerTank;
    
    public InputController(Tank playerTank) {
        this.playerTank = playerTank;
    }
    
    public boolean handleInput(GridPoint2 obstacleCoordinates) {
        for (Direction direction : Direction.values()) {
            if (direction.isPressed()) {
                return playerTank.move(direction, obstacleCoordinates);
            }
        }
        return false;
    }
    
    public boolean handleInput(GridPoint2... obstacles) {
        for (Direction direction : Direction.values()) {
            if (direction.isPressed()) {
                for (GridPoint2 obstacle : obstacles) {
                    if (obstacle.equals(direction.apply(playerTank.getCoordinates()))) {
                        return false; 
                    }
                }
                return playerTank.move(direction, null);
            }
        }
        return false;
    }
}