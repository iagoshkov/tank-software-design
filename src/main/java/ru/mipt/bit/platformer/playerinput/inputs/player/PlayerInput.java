package ru.mipt.bit.platformer.playerinput.inputs.player;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.entity.objects.base.GameObject;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractActionFactory;
import ru.mipt.bit.platformer.playerinput.inputs.ActionGenerator;
import ru.mipt.bit.platformer.playerinput.inputs.InputActions;

import java.util.*;

public class PlayerInput implements ActionGenerator {
    private final InputActions inputActions;
    private final GameObject object;

    public PlayerInput(InputActions actions, GameObject object) {
        this.inputActions = actions;
        this.object = object;
    }

    @Override
    public AbstractAction getAction() {
        for (Map.Entry<Integer, AbstractActionFactory> entry : inputActions.getKeyActions().entrySet()) {
            if (Gdx.input.isKeyPressed(entry.getKey())) {
                return entry.getValue().create(object);
            }
        }
        return null;
    }

    @Override
    public List<AbstractAction> getActions() {
        List<AbstractAction> actions = new ArrayList<>();
        AbstractAction action = getAction();

        if (action != null) {
            actions.add(action);
        }

        return actions;
    }
}
