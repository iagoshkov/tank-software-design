package ru.mipt.bit.platformer.controller;

import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.model.Tank;
import java.util.List;

public class ToggleHealthDisplayCommand {
    private final List<Tank> tanks;
    private boolean healthBarsVisible = false;
    
    public ToggleHealthDisplayCommand(List<Tank> tanks) {
        this.tanks = tanks;
    }
    
    public boolean execute() {
        if (com.badlogic.gdx.Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            healthBarsVisible = !healthBarsVisible;
            
            for (Tank tank : tanks) {
                tank.setHealthBarVisible(healthBarsVisible);
            }
            
            return true;
        }
        return false;
    }
    
    public boolean isHealthBarsVisible() {
        return healthBarsVisible;
    }
}