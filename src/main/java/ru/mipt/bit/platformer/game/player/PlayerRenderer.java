package ru.mipt.bit.platformer.game.player;

import ru.mipt.bit.platformer.game.controls.MoveCommand;
import ru.mipt.bit.platformer.game.level.LevelEntity;
import ru.mipt.bit.platformer.game.level.LevelRenderer;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class PlayerRenderer {
    /*
    Класс, ответственный за отображение перемещения игрока на уровне. Обертка над логикой PlayerMoveLogic для рендера
    текстуры игрока на поле.
     */

    private final PlayerMoveLogic playerMoveLogic;
    private final LevelRenderer levelRenderer;
    private final LevelEntity playerTexture;
    public static final float MOVEMENT_SPEED = 0.4f;
    private float playerMovementProgress = 1f;


    public PlayerRenderer(LevelEntity player, PlayerMoveLogic playerMoveLogic, LevelRenderer levelRenderer) {
        this.playerTexture = player;
        this.playerMoveLogic = playerMoveLogic;
        this.levelRenderer = levelRenderer;
    }

    public void startMove(MoveCommand command) {
        if (isEqual(playerMovementProgress, 1f)) {
            boolean hasMoved = playerMoveLogic.makeMove(command);
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
                playerTexture, playerMoveLogic.getDestination(), playerMovementProgress
        );
    }
}
