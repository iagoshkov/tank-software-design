package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class PlayerMoveOperator {
    /*
    Класс, ответственный за перемещение игрока по полю. Также следит, чтобы игрок не врезался в препятствия.
     */

    private static final float MOVEMENT_SPEED = 0.4f;
    private final Player player;
    private final List<LevelObject> obstacles;
    private float playerMovementProgress = 1f;

    public PlayerMoveOperator(Player player, List<LevelObject> obstacles) {
        this.player = player;
        this.obstacles = obstacles;
    }

    public void makeMove(PlayerMove move) {
        GridPoint2 oldCoordinates = player.getDestination().cpy();
        if (isEqual(playerMovementProgress, 1f)) {
            movePlayer(move);
            if (!hasHitObstacle()) {
                playerMovementProgress = 0f;
            } else {
                player.setDestination(oldCoordinates);
            }
        }
    }

    public void confirmMove(float deltaTime) {
        playerMovementProgress = continueProgress(playerMovementProgress, deltaTime, MOVEMENT_SPEED);
        if (isEqual(playerMovementProgress, 1f)) {
            player.setCoordinates(player.getDestination());
        }
    }

    private boolean hasHitObstacle() {
        for (LevelObject obstacle : obstacles) {
            if (obstacle.getCoordinates().equals(player.getDestination())) {
                return true;
            }
        }
        return false;
    }

    private void movePlayer(PlayerMove move) {
        GridPoint2 destCoordinates = player.getDestination();
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
}
