package ru.mipt.bit.platformer.control;

/** Application: shows or hides health bars. */
public class ToggleHealthBarsCommand implements Command {

    private final Toggle toggle;

    public ToggleHealthBarsCommand(Toggle toggle) {
        this.toggle = toggle;
    }

    @Override
    public void execute() {
        toggle.toggle();
    }
}
