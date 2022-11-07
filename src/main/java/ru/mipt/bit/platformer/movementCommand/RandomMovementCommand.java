package ru.mipt.bit.platformer.movementCommand;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.OnScreenObject;
import ru.mipt.bit.platformer.objects.Tank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static com.badlogic.gdx.Input.Keys.*;

public class RandomMovementCommand implements MovementCommand {
    private final Random rd = new Random();

    @Override
    public HashMap<Tank, TankAction> getTankActions(ArrayList<OnScreenObject> obstacles, ArrayList<Tank> tanks, int fieldWidth, int fieldHeight) {
        HashMap<Tank, TankAction> actions = new HashMap<>();
        for (var player : tanks) {
            if (player.isManuallyControlled())
                continue;

            // shoot with 20% probability
            if (rd.nextInt(5) == 0) {
                actions.put(player, TankAction.SHOOT);
                continue;
            }

            GridPoint2 movement = new GridPoint2(0, 0);
            if (rd.nextInt(2) == 0) {
                movement.x += rd.nextInt(3) - 1;
            } else {
                movement.y += rd.nextInt(3) - 1;
            }

            TankAction action;

            if (movement.y == 1) {
                action = TankAction.MOVE_UP;
            } else if (movement.x == -1) {
                action = TankAction.MOVE_LEFT;
            } else if (movement.y == -1) {
                action = TankAction.MOVE_DOWN;
            } else if (movement.x == 1) {
                action = TankAction.MOVE_RIGHT;
            } else {
                action = TankAction.WAIT;
            }

            actions.put(player, action);
        }
        return actions;
    }

    public RandomMovementCommand() {
    }
}
