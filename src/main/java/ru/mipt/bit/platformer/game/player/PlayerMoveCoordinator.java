package ru.mipt.bit.platformer.game.player;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.level.LevelEntity;

import java.util.List;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class PlayerMoveCoordinator {
    /*
    Класс, ответственный за перемещение игрока по полю. Также следит, чтобы игрок не врезался в препятствия.
     */

    private static final float MOVEMENT_SPEED = 0.4f;
    private final Player player;
    private GridPoint2 playerDestination;
    private final List<LevelEntity> obstacles;
    private float playerMovementProgress = 1f;

    public PlayerMoveCoordinator(Player player, List<LevelEntity> obstacles) {
        this.player = player;
        this.obstacles = obstacles;

        this.playerDestination = player.getCoordinates();
    }

    public void makeMove(PlayerMove move) {
        GridPoint2 oldCoordinates = playerDestination.cpy();
        if (isEqual(playerMovementProgress, 1f)) {
            movePlayer(move);
            if (!hasHitObstacle()) {
                playerMovementProgress = 0f;
            } else {
                playerDestination = oldCoordinates;
            }
        }
    }

    public void confirmMove(float deltaTime) {
        playerMovementProgress = continueProgress(playerMovementProgress, deltaTime, MOVEMENT_SPEED);
        if (isEqual(playerMovementProgress, 1f)) {
            player.setCoordinates(playerDestination);
        }
    }

    private boolean hasHitObstacle() {
        for (LevelEntity obstacle : obstacles) {
            if (obstacle.getCoordinates().equals(playerDestination)) {
                return true;
            }
        }
        return false;
    }

    private void movePlayer(PlayerMove move) {
        GridPoint2 destCoordinates = playerDestination;
        switch (move) {
            case UP:
                destCoordinates.y++;
                player.setRotation(90f);
                break;
            case DOWN:
                destCoordinates.y--;
                player.setRotation(-90f);
                break;
            case LEFT:
                destCoordinates.x--;
                player.setRotation(-180f);
                break;
            case RIGHT:
                destCoordinates.x++;
                player.setRotation(0f);
                break;
        }
    }

    public float getMovementProgress() {
        return playerMovementProgress;
    }

    public GridPoint2 getDestination() {
        return playerDestination;
    }
}
