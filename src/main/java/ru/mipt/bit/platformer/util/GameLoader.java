package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.levels.DrawableLevel;
import ru.mipt.bit.platformer.objects.Drawable;
import ru.mipt.bit.platformer.objects.Movable;

import java.util.Collection;

public interface GameLoader {
    Batch getBatch();

    DrawableLevel getLevel();

    Collection<Drawable> getDrawables();

    Collection<Movable> getMovables();
}
