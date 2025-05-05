package ru.mipt.bit.platformer.playerinput.actions.base;

public interface AbstractActionFactory<T> {
    AbstractAction create(T object);
}
