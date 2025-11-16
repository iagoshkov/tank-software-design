package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.commands.MoveCommand;
import ru.mipt.bit.platformer.commands.ShootCommand;
import ru.mipt.bit.platformer.commands.ToggleHealthBarCommand;
import ru.mipt.bit.platformer.controllers.InputController;
import ru.mipt.bit.platformer.objects.GameObject;
import ru.mipt.bit.platformer.objects.Player;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.util.Direction;

public class InputHandler {
    private final Object controlledObject;
    private final InputController inputController;
    private final Level level;
    private final ToggleHealthBarCommand toggleHealthBarCommand;

    public InputHandler(Player player, InputController inputController, Level level) {
        this.controlledObject = player;
        this.inputController = inputController;
        this.level = level;
        this.toggleHealthBarCommand = new ToggleHealthBarCommand(level);
    }

    public InputHandler(Tank tank, InputController inputController, Level level) {
        this.controlledObject = tank;
        this.inputController = inputController;
        this.level = level;
        this.toggleHealthBarCommand = new ToggleHealthBarCommand(level);
    }

    public void handleInput() {
        if (controlledObject instanceof Player && Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.L)) {
            toggleHealthBarCommand.execute();
        }

        if (inputController.shouldShoot()) {
            if (controlledObject instanceof Player) {
                ((Player) controlledObject).shoot();
            } else if (controlledObject instanceof Tank) {
                ShootCommand shootCommand = new ShootCommand((Tank) controlledObject);
                shootCommand.execute();
            }
        }

        if (!isMoving()) {
            Direction direction = inputController.getInputDirection();
            if (direction != Direction.NULL && isMoveValid(direction)) {
                if (controlledObject instanceof Player) {
                    ((Player) controlledObject).move(direction);
                } else if (controlledObject instanceof Tank) {
                    MoveCommand moveCommand = new MoveCommand((Tank) controlledObject, direction);
                    moveCommand.execute();
                }
            }
        }
    }

    private boolean isMoveValid(Direction direction) {
        GridPoint2 currentPosition = getCurrentPosition();
        GridPoint2 nextPosition = direction.getNextPosition(currentPosition);

        if (!level.isPositionValid(nextPosition)) return false;
        if (level.isPositionOccupied(nextPosition)) return false;
        if (level.isPositionBlockedByTree(nextPosition)) return false;

        if (controlledObject instanceof Tank) {
            Tank tank = (Tank) controlledObject;
            if (!tank.isPlayerControlled()) {
                if (isPositionOccupiedByPlayer(nextPosition)) return false;
            }
        }

        return true;
    }

    private boolean isPositionOccupiedByPlayer(GridPoint2 position) {
        for (GameObject obj : level.getGameObjects()) {
            if (obj instanceof Player) {
                Player player = (Player) obj;
                if (player.getCoordinates().equals(position) || 
                    (player.isMoving() && player.getDestinationCoordinates().equals(position))) {
                    return true;
                }
            }
        }
        return false;
    }

    private GridPoint2 getCurrentPosition() {
        if (controlledObject instanceof Player) {
            return ((Player) controlledObject).getCoordinates();
        } else if (controlledObject instanceof Tank) {
            return ((Tank) controlledObject).getCoordinates();
        }
        return null;
    }

    private boolean isMoving() {
        if (controlledObject instanceof Player) {
            return ((Player) controlledObject).isMoving();
        } else if (controlledObject instanceof Tank) {
            return ((Tank) controlledObject).isMoving();
        }
        return false;
    }
}