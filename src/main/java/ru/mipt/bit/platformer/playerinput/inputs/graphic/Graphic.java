package ru.mipt.bit.platformer.playerinput.inputs.graphic;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.entity.draw.base.LevelGraphic;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractActionFactory;
import ru.mipt.bit.platformer.playerinput.inputs.ActionGenerator;
import ru.mipt.bit.platformer.playerinput.inputs.InputActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Graphic implements ActionGenerator {
    private final InputActions inputActions;
    private final LevelGraphic levelGraphic;

    public Graphic(InputActions actions, LevelGraphic levelGraphic) {
        this.inputActions = actions;
        this.levelGraphic = levelGraphic;
    }

    @Override
    public AbstractAction getAction() {
        for (Map.Entry<Integer, AbstractActionFactory> entry : inputActions.getKeyActions().entrySet()) {
            if (Gdx.input.isKeyPressed(entry.getKey())) {
                return entry.getValue().create(levelGraphic);
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
