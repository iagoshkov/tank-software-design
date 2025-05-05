package ru.mipt.bit.platformer.playerinput.keys;

import ru.mipt.bit.platformer.playerinput.actions.MoveAction;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.Input.Keys.*;

public class KeyActions {
    private static final Map<Integer, AbstractAction> associationKeys = new HashMap<>();

    private static void setMoves() {
        associationKeys.put(RIGHT, new MoveAction(Direction.RIGHT));
        associationKeys.put(D, new MoveAction(Direction.RIGHT));
        associationKeys.put(LEFT, new MoveAction(Direction.LEFT));
        associationKeys.put(A, new MoveAction(Direction.LEFT));
        associationKeys.put(UP, new MoveAction(Direction.UP));
        associationKeys.put(W, new MoveAction(Direction.UP));
        associationKeys.put(DOWN, new MoveAction(Direction.DOWN));
        associationKeys.put(S, new MoveAction(Direction.DOWN));
    }

    public static Map<Integer, AbstractAction> getKeyActions() {
        setMoves();
        return associationKeys;
    }
}
