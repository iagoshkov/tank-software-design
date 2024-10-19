package ru.mipt.bit.platformer.keys;

import ru.mipt.bit.platformer.objects.Movable;
import ru.mipt.bit.platformer.objects.GameObject;

import java.util.Collection;

import static ru.mipt.bit.platformer.util.GdxGameUtils.decrementedY;

public class DOWN extends MovementKey {

    public DOWN(Collection<? extends GameObject> obstacles, Collection<Movable> movables, int[] keys, Direction dir) {
        super(obstacles, movables, keys, dir, null);
    }
}
