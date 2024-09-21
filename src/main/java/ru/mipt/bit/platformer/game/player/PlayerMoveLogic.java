package ru.mipt.bit.platformer.game.player;

import ru.mipt.bit.platformer.game.controls.MoveCommand;
import ru.mipt.bit.platformer.game.entities.Coordinates;
import ru.mipt.bit.platformer.game.entities.GameEntity;

import java.util.List;

public class PlayerMoveLogic {
    /*
    Класс, ответственный за перемещение игрока по полю (логическое). Также следит, чтобы игрок не врезался в препятствия.
     */

    private final Player player;
    private Coordinates playerDestination;
    private final List<GameEntity> obstacles;

    public PlayerMoveLogic(Player player, List<GameEntity> obstacles) {
        this.player = player;
        this.obstacles = obstacles;

        this.playerDestination = player.getCoordinates();
    }

    public boolean makeMove(MoveCommand direction) {
        Coordinates oldCoordinates = new Coordinates(playerDestination.x, playerDestination.y);
        movePlayer(direction);
        if (!hasHitObstacle()) {
            return true;
        } else {
            playerDestination = oldCoordinates;
            return false;
        }
    }

    public void confirmMove() {
        player.setCoordinates(playerDestination);
    }

    private boolean hasHitObstacle() {
        for (GameEntity obstacle : obstacles) {
            if (obstacle.getCoordinates().equals(playerDestination)) {
                return true;
            }
        }
        return false;
    }

    private void movePlayer(MoveCommand direction) {
        player.setRotation(direction.getRotation());
        playerDestination.x += direction.getShiftX();
        playerDestination.y += direction.getShiftY();
    }

    public Coordinates getDestination() {
        return playerDestination;
    }

    public void setPlayerCoordinates(Coordinates destination) {
        this.playerDestination = destination;
        confirmMove();
    }
}
