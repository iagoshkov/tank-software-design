package ru.mipt.bit.platformer.logic.movements;

import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Player;

public interface MovementsProcessor {
    void processMoveCommand(Player player, Direction direction);
}
