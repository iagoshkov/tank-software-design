package ru.mipt.bit.platformer.commands;

import ru.mipt.bit.platformer.Level;
import ru.mipt.bit.platformer.objects.GameObject;

public class ToggleHealthBarCommand implements Command {
    private final Level level;
    private boolean healthBarsVisible = false;

    public ToggleHealthBarCommand(Level level) {
        this.level = level;
    }

    @Override
    public void execute() {
        healthBarsVisible = !healthBarsVisible;
        
        for (GameObject obj : level.getGameObjects()) {
            obj.setShowHealthBar(healthBarsVisible);
        }
        
        System.out.println("Health bars: " + (healthBarsVisible ? "ON" : "OFF"));
    }

    @Override
    public void undo() {
        execute(); 
    }

    public boolean isHealthBarsVisible() {
        return healthBarsVisible;
    }
}