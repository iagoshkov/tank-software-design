package ru.mipt.bit.platformer.playerinput.actions.actions;

import ru.mipt.bit.platformer.entity.draw.decorators.base.Toggle;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;

public class ToggleAction implements AbstractAction {
    private final Toggle toggle;

    public ToggleAction(Toggle toggle) {
        this.toggle = toggle;
    }

    public void apply() {
        toggle.switchToggle();
    }
}
