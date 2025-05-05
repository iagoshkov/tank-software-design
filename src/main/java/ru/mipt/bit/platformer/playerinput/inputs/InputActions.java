package ru.mipt.bit.platformer.playerinput.inputs;

import ru.mipt.bit.platformer.playerinput.actions.base.AbstractActionFactory;

import java.util.Map;

public interface InputActions {
    void registerAction(Integer actionKey, AbstractActionFactory action);
    Map<Integer, AbstractActionFactory> getKeyActions();
}
