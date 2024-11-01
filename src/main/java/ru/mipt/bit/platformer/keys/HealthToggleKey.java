package ru.mipt.bit.platformer.keys;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.objects.Destroyable;
import ru.mipt.bit.platformer.objects.Drawable;

import java.util.Arrays;
import java.util.Collection;

public class HealthToggleKey implements Key {

    protected final Collection<Drawable> drawables;
    private final int[] keys;
    private static boolean pressed = false;

    public HealthToggleKey(Collection<Drawable> drawables, int[] keys) {
        this.drawables = drawables;
        this.keys = keys;
    }

    @Override
    public boolean isPressed() {
        if (Arrays.stream(keys).anyMatch(key -> Gdx.input.isKeyPressed(key))) {
            pressed = !pressed;
        }
        return pressed;
    }

    @Override
    public void action() {//
        for (Drawable drawable : drawables) {
            drawable.showHealth();
        }
    }
}
