package ru.mipt.bit.platformer.controller;

import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.model.Tank;

public class ShootCommand {
    private final Tank tank;
    
    public ShootCommand(Tank tank) {
        this.tank = tank;
    }
    
    public boolean execute() {
        if (com.badlogic.gdx.Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            return tank.shoot();
        }
        return false;
    }
    
    public void executeAI() {
        tank.shoot();
    }
}