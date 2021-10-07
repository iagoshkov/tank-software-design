package ru.mipt.bit.platformer.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.mipt.bit.platformer.movables.Movable;

@AllArgsConstructor
@Getter
public class Player {
    private final String name;
    private final Movable movableObject;
}
