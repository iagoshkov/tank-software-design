package ru.mipt.bit.platformer.keys;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.levels.Level;
import ru.mipt.bit.platformer.objects.AI;
import ru.mipt.bit.platformer.objects.Movable;
import ru.mipt.bit.platformer.objects.GameObject;

import java.util.Arrays;
import java.util.Collection;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class MovementKey implements Key {

    protected final Collection<? extends GameObject> obstacles;
    private   final Collection<Movable> movables;
    private final int[] keys;
    private final Direction direction;
    private final Level level;

    public MovementKey(Collection<? extends GameObject> obstacles,
                       Collection<Movable> movables,
                       int[] keys,
                       Direction direction,
                       Level level) {
        this.obstacles = obstacles;
        this.movables  =  movables;
        this.keys = keys;
        this.direction = direction;
        this.level = level;
    }

    @Override
    public boolean isPressed() {
        return Arrays.stream(keys).anyMatch(key -> Gdx.input.isKeyPressed(key));
    }

    @Override
    public void action() {
        for (Movable movable : movables) {
            if (!(movable instanceof AI) && movable.canMoveToDirection(direction, obstacles, level)) {
                movable.move(direction);
            }
        }
    }
}

