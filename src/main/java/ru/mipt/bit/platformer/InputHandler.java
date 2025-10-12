package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.controllers.InputController;
import ru.mipt.bit.platformer.objects.Player;
import ru.mipt.bit.platformer.util.Direction;

public class InputHandler {
    private final Player player;
    private final InputController inputController;

    public InputHandler(Player player, InputController inputController) {
        this.player = player;
        this.inputController = inputController;
    }

    public void handleInput() {
        if (player.isMoving()) return;

        Direction direction = inputController.getInputDirection();
        if (direction != Direction.NULL) {
            GridPoint2 nextPosition = direction.getNextPosition(player.getCoordinates());
            // Здесь должна быть проверка через Level, а не прямое сравнение с Tree
            player.move(direction);
        } else if (inputController.isShootPressed()) {
            System.out.println("Shoot action triggered");
        }
    }
}