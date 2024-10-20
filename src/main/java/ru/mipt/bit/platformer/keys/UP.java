package ru.mipt.bit.platformer.keys;

import ru.mipt.bit.platformer.levels.Level;
import ru.mipt.bit.platformer.objects.Movable;
import ru.mipt.bit.platformer.objects.GameObject;

import java.util.Collection;

public class UP extends MovementKey {

    public UP(Collection<? extends GameObject> obstacles, Collection<Movable> movables, int[] keys, Direction dir, Level level) {
        super(obstacles, movables, keys, dir, level);
    }
}
