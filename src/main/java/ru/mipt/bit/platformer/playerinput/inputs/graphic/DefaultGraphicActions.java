package ru.mipt.bit.platformer.playerinput.inputs.graphic;

import ru.mipt.bit.platformer.playerinput.actions.base.AbstractActionFactory;
import ru.mipt.bit.platformer.playerinput.actions.factories.ToggleActionFactory;
import ru.mipt.bit.platformer.playerinput.inputs.DefaultInputActions;
import ru.mipt.bit.platformer.playerinput.inputs.InputActions;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.Input.Keys.*;

public class DefaultGraphicActions implements DefaultInputActions {
    private final Map<Integer, AbstractActionFactory> associationKeys = new HashMap<>();

    public DefaultGraphicActions() {
        setDrawActions();
    }

    private void setDrawActions() {
        associationKeys.put(L, new ToggleActionFactory());
    }

    @Override
    public void registerActions(InputActions playerInput) {
        for (Map.Entry<Integer, AbstractActionFactory> entry : associationKeys.entrySet()) {
            playerInput.registerAction(entry.getKey(), entry.getValue());
        }
    }
}
