package ru.mipt.bit.platformer.playerinput.inputs.graphic;

import ru.mipt.bit.platformer.playerinput.actions.base.AbstractActionFactory;
import ru.mipt.bit.platformer.playerinput.inputs.InputActions;

import java.util.HashMap;
import java.util.Map;

public class GraphicActions implements InputActions {
    private final Map<Integer, AbstractActionFactory> associationKeys = new HashMap<>();

    @Override
    public void registerAction(Integer actionKey, AbstractActionFactory action) {
        associationKeys.put(actionKey, action);
    }

    @Override
    public Map<Integer, AbstractActionFactory> getKeyActions() {
        return associationKeys;
    }
}
