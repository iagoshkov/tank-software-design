package ru.mipt.bit.platformer.controllers;

import ru.mipt.bit.platformer.util.Direction;

public interface InputController {
    Direction getInputDirection();
    boolean isShootPressed();
}