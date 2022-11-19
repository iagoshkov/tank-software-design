package ru.mipt.bit.platformer.movementCommand;

import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.objects.OnScreenObject;
import ru.mipt.bit.platformer.objects.Tank;

import java.util.Collection;
import java.util.HashMap;

import static com.badlogic.gdx.Input.Keys.*;

public class UserInputMovementCommand implements MovementCommand {
    private final Input input;

    @Override
    public HashMap<Tank, TankAction> getTankActions(Collection<OnScreenObject> obstacles, Collection<Tank> tanks, int fieldWidth, int fieldHeight) {
        HashMap<Tank, TankAction> ret = new HashMap<>();
        for (var player : tanks) {
            if (!player.isManuallyControlled())
                continue;
            TankAction action;
            if (input.isKeyPressed(UP) || input.isKeyPressed(W)) {
                action = TankAction.MOVE_UP;
            } else if (input.isKeyPressed(LEFT) || input.isKeyPressed(A)) {
                action = TankAction.MOVE_LEFT;
            } else if (input.isKeyPressed(DOWN) || input.isKeyPressed(S)) {
                action = TankAction.MOVE_DOWN;
            } else if (input.isKeyPressed(RIGHT) || input.isKeyPressed(D)) {
                action = TankAction.MOVE_RIGHT;
            } else if (input.isKeyPressed(SPACE)) {
                action = TankAction.SHOOT;
            } else {
                action = TankAction.WAIT;
            }
            ret.put(player, action);
        }
        return ret;
    }

    public UserInputMovementCommand(Input input) {
        this.input = input;
    }
}
