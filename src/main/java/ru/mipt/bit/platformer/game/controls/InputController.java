package ru.mipt.bit.platformer.game.controls;

import java.util.List;

public class InputController {
    /*
    Класс, считывающий пользвательский ввод с различных устройств и возвращающий команду от пользователя.
     */

    private final InputHandler keyboard = new KeyboardHandler();

    public UserCommand getUserCommand() {
        List<InputHandler> devices = List.of(keyboard);
        for (InputHandler device : devices) {
            UserCommand pressedKey = device.handleUserInput();
            if (pressedKey != null) return pressedKey;
        }
        return null;
    }
}
