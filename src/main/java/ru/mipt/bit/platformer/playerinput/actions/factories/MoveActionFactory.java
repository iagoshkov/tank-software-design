package ru.mipt.bit.platformer.playerinput.actions.factories;

import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.entity.objects.base.GameObject;
import ru.mipt.bit.platformer.entity.objects.base.Movable;
import ru.mipt.bit.platformer.playerinput.actions.actions.MoveAction;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractActionFactory;
import ru.mipt.bit.platformer.playerinput.inputs.Direction;

public class MoveActionFactory implements AbstractActionFactory<GameObject> {
    private final Direction direction;
    private final Level level;

    public MoveActionFactory(Direction direction, Level level) {
        this.direction = direction;
        this.level = level;
    }

    @Override
    public AbstractAction create(GameObject object) {
        return new MoveAction(direction, level, (Movable) object);
    }
}
