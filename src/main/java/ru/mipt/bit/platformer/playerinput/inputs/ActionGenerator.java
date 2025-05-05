package ru.mipt.bit.platformer.playerinput.inputs;

import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;

import java.util.List;

public interface ActionGenerator {
    AbstractAction getAction();
    List<AbstractAction> getActions();
}
