package ru.mipt.bit.platformer.controllers;


import com.badlogic.gdx.math.GridPoint2;
import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.service.Colliding;
import ru.mipt.bit.platformer.entities.Direction;
import ru.mipt.bit.platformer.entities.Player;
import ru.mipt.bit.platformer.keyboard.KeyboardChecker;
import ru.mipt.bit.platformer.service.ActionMapper;

import java.util.List;


@RequiredArgsConstructor
public class PlayerControllerImpl implements PlayerController {

    private final ActionMapper actionMapper;
    private final KeyboardChecker checker;
    private final Player player;
    private final List<Colliding> obstacles;


    public void movePlayer(float deltaTime) {
        for (int button: actionMapper.getMappedKeys()) {
            if (checker.isButtonPressed(button)) {
                Direction direction = actionMapper.getDirectionByKey(button);
                if (collisionImpossible(direction)) {
                    player.getMovableObject().triggerMovement(direction);
                }
                player.getMovableObject().setRotation(direction.getRotation());
            }
        }
        player.getMovableObject().move(deltaTime);
    }

    private boolean collisionImpossible(Direction direction) {
        GridPoint2 expected = player.getMovableObject().getCoordinatesAfterShift(direction);
        for (Colliding obstacle : obstacles) {
            if (obstacle.isCollisionPossible(expected)) {
                return false;
            }
        }
        return true;
    }

}
