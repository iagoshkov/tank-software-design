package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.Instructions.Direction;
import ru.mipt.bit.platformer.Instructions.Instruction;

import java.util.HashMap;
import java.util.Map;


public class InputController {
    private final Map<Integer, Instruction> keyToInstructionMap;

    InputController() {
        keyToInstructionMap = new HashMap<>();
    }

    public void addMapping(int key, Direction direction) {
        keyToInstructionMap.put(key, direction);
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