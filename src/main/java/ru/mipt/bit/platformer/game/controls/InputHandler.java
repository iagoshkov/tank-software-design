package ru.mipt.bit.platformer.game.controls;

public interface InputHandler {
    /*
    Интерфейс считывания нажатия кнопок с устройства.
     */

    UserCommand handleUserInput();
}
