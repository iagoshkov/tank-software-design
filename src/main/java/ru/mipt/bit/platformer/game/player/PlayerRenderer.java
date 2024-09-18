package ru.mipt.bit.platformer.game.player;

import ru.mipt.bit.platformer.game.controls.MoveCommand;
import ru.mipt.bit.platformer.game.level.LevelRenderer;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class PlayerRenderer {
    /*
    Класс, ответственный за отображения перемещения игрока на уровне. Обертка над логикой PlayerMoveLogic для рендера.
     */

    private final PlayerMoveLogic playerMoveLogic;
    private final LevelRenderer levelRenderer;
    private final Player player;
    public static final float MOVEMENT_SPEED = 0.4f;
    private float playerMovementProgress = 1f;


    public PlayerRenderer(Player player, PlayerMoveLogic playerMoveLogic, LevelRenderer levelRenderer) {
        this.player = player;
        this.playerMoveLogic = playerMoveLogic;
        this.levelRenderer = levelRenderer;
    }

    public void startMove(MoveCommand command) {
        if (isEqual(playerMovementProgress, 1f)) {
            boolean hasMoved = playerMoveLogic.makeMove(command);
            System.out.println(hasMoved);
            if (hasMoved) {
                playerMovementProgress = 0f;
            }
        }
    }

    public void movePlayer(float deltaTime) {
        playerMovementProgress = continueProgress(playerMovementProgress, deltaTime, MOVEMENT_SPEED);
        if (isEqual(playerMovementProgress, 1f)) {
            playerMoveLogic.confirmMove();
        }
        levelRenderer.shiftEntity(
                player.getPlayerEntity(), playerMoveLogic.getDestination(), playerMovementProgress
        );
    }
}
