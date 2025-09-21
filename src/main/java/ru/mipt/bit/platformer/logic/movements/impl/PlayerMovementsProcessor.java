package ru.mipt.bit.platformer.logic.movements.impl;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.input.InputController;
import ru.mipt.bit.platformer.logic.movements.MovementsProcessor;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Player;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import static ru.mipt.bit.platformer.util.GdxGameUtils.incrementedX;

public class PlayerMovementsProcessor implements MovementsProcessor {

    @Override
    public void processMoveCommand(Player player, Direction direction) {
        if (isEqual(player.getPlayerMovementProgress(), 1f)) {
            GridPoint2 candidate = switch (direction) {
                case UP -> incrementedY(player.getPlayerCoordinates());
                case LEFT -> decrementedX(player.getPlayerCoordinates());
                case DOWN -> decrementedY(player.getPlayerCoordinates());
                case RIGHT -> incrementedX(player.getPlayerCoordinates());
            };
            if (!treeObstacleCoordinates.equals(candidate)) {
                switch (direction) {
                    case UP -> player.getPlayerDestinationCoordinates().y++;
                    case LEFT -> player.getPlayerDestinationCoordinates().x--;
                    case DOWN -> player.getPlayerDestinationCoordinates().y--;
                    case RIGHT -> player.getPlayerDestinationCoordinates().x++;
                }
                player.setPlayerMovementProgress(0f);
            }
            player.playerRotation = switch (direction) {
                case UP -> 90f;
                case LEFT -> -180f;
                case DOWN -> -90f;
                case RIGHT -> 0f;
            };
        }
    }
}
