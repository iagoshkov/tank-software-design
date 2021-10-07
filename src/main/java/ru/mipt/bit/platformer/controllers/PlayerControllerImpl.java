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


    @Override
    public void movePlayer(float deltaTime) {
        for (int key: actionMapper.getMappedKeys()) {
            if (checker.isKeyPressed(key)) {
                Direction direction = actionMapper.getDirectionByKey(key);
                player.getMovableObject().setRotation(direction.getRotation());
                if (collisionImpossible(direction)) {
                    player.getMovableObject().triggerMovement(direction);
                }
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
