package ru.mipt.bit.platformer.playerinput.inputs.ai;

import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractActionFactory;
import ru.mipt.bit.platformer.playerinput.actions.factories.MoveActionFactory;
import ru.mipt.bit.platformer.playerinput.inputs.InputActions;
import ru.mipt.bit.platformer.playerinput.inputs.DefaultInputActions;
import ru.mipt.bit.platformer.playerinput.inputs.Direction;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.Input.Keys.*;

public class DefaultAIActions implements DefaultInputActions {
    private final Map<Integer, AbstractActionFactory> associationKeys = new HashMap<>();

    public DefaultAIActions(Level level) {
        setMoves(level);
    }

    private void setMoves(Level level) {
        associationKeys.put(RIGHT, new MoveActionFactory(Direction.RIGHT, level));
        associationKeys.put(LEFT, new MoveActionFactory(Direction.LEFT, level));
        associationKeys.put(UP, new MoveActionFactory(Direction.UP, level));
        associationKeys.put(DOWN, new MoveActionFactory(Direction.DOWN, level));
    }

    @Override
    public void registerActions(InputActions playerInput) {
        for (Map.Entry<Integer, AbstractActionFactory> entry : associationKeys.entrySet()) {
            playerInput.registerAction(entry.getKey(), entry.getValue());
        }
    }
}
