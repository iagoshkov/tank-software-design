package ru.mipt.bit.platformer.graphics;

import ru.mipt.bit.platformer.control.Toggle;

/** Infrastructure, adapter for Toggle: keeps whether health bars are shown. */
public class HealthBarToggle implements Toggle {

    private boolean visible;

    public boolean isVisible() {
        return visible;
    }

    @Override
    public void toggle() {
        visible = !visible;
    }
}
