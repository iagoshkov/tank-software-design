package ru.mipt.bit.platformer.movement;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.models.Player;

import java.util.List;

public class PlayerMovement implements InputHandler {

    private final Player player;
    private final List<GridPoint2> obstacles;
    private final InputController inputController;

    public PlayerMovement(Player player, List<GridPoint2> obstacles, InputController inputController) {
        this.player = player;
        this.obstacles = obstacles;
        this.inputController = inputController;
    }

    @Override
    public void handleInput(float deltaTime) {
        if (player.isMoving()) return;

        GridPoint2 current = player.getCoordinates();
        GridPoint2 target = new GridPoint2(current);
        float rotation = player.getRotation();

        if (inputController.isUpPressed()) {
            target.add(0, 1);
            rotation = 90f;
        } else if (inputController.isDownPressed()) {
            target.add(0, -1);
            rotation = 270f;
        } else if (inputController.isLeftPressed()) {
            target.add(-1, 0);
            rotation = 180f;
        } else if (inputController.isRightPressed()) {
            target.add(1, 0);
            rotation = 0f;
        }

        if (!target.equals(current)) {
            if (!obstacles.contains(target)) {
                player.startMovement(target, rotation);
            } else {
                player.startMovement(current, rotation);
            }
        }
    }
}
