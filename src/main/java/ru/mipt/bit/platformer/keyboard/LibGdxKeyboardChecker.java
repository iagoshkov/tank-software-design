package ru.mipt.bit.platformer.keyboard;

import com.badlogic.gdx.Input;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LibGdxKeyboardChecker implements KeyboardChecker {

    private final Input gdxInput;

    @Override
    public boolean isButtonPressed(int button) {
        return gdxInput.isButtonPressed(button);
    }
}
