package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.Instructions.Direction;
import ru.mipt.bit.platformer.Instructions.Instruction;

import java.util.HashMap;
import java.util.Map;


public class InputController {
    private final Map<Integer, Instruction> keyToInstructionMap = new HashMap<>();

    InputController() {
        addMapping(Input.Keys.UP, Direction.UP);
        addMapping(Input.Keys.W, Direction.UP);
        addMapping(Input.Keys.LEFT, Direction.LEFT);
        addMapping(Input.Keys.A, Direction.LEFT);
        addMapping(Input.Keys.DOWN, Direction.DOWN);
        addMapping(Input.Keys.S, Direction.DOWN);
        addMapping(Input.Keys.RIGHT, Direction.RIGHT);
        addMapping(Input.Keys.D, Direction.RIGHT);
    }

    public void addMapping(int key, Instruction instruction) {
        keyToInstructionMap.put(key, instruction);
    }

    public Instruction getInstruction() {
        for (Integer key : keyToInstructionMap.keySet()) {
            if (Gdx.input.isKeyPressed(key)) {
                return keyToInstructionMap.get(key);
            }
        }
        return null;
    }
}