package ru.mipt.bit.platformer.movementCommand;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.OnScreenObject;
import ru.mipt.bit.platformer.objects.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RandomMovementCommand implements MovementCommand {
    private final Random rd = new Random();

    @Override
    public HashMap<Player, GridPoint2> getTankActions(ArrayList<OnScreenObject> obstacles, ArrayList<Player> players, int fieldWidth, int fieldHeight) {
        HashMap<Player, GridPoint2> actions = new HashMap<>();
        for (var player : players) {
            if (player.isManuallyControlled())
                continue;
            GridPoint2 movement = new GridPoint2(0, 0);

            if (rd.nextInt(2) == 0) {
                movement.x += rd.nextInt(3) - 1;
            } else {
                movement.y += rd.nextInt(3) - 1;
            }
            actions.put(player, movement);
        }
        return actions;
    }

    public RandomMovementCommand() {
    }
}
