package ru.mipt.bit.platformer.movementCommand;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.OnScreenObject;
import ru.mipt.bit.platformer.objects.Player;

import java.util.ArrayList;
import java.util.HashMap;

import static com.badlogic.gdx.Input.Keys.*;

public class UserInputMovementCommand implements MovementCommand {
    private final Input input;

    @Override
    public HashMap<Player, GridPoint2> getTankActions(ArrayList<OnScreenObject> obstacles, ArrayList<Player> players, int fieldWidth, int fieldHeight) {
        HashMap<Player, GridPoint2> ret = new HashMap<>();
        for (var player : players) {
            if (!player.isManuallyControlled())
                continue;
            GridPoint2 movement = new GridPoint2(0, 0);
            if (input.isKeyPressed(UP) || input.isKeyPressed(W)) {
                movement.y = 1;
            }
            if (input.isKeyPressed(LEFT) || input.isKeyPressed(A)) {
                movement.x = -1;
            }
            if (input.isKeyPressed(DOWN) || input.isKeyPressed(S)) {
                movement.y = -1;
            }
            if (input.isKeyPressed(RIGHT) || input.isKeyPressed(D)) {
                movement.x = 1;
            }
            ret.put(player, movement);
        }
        return ret;
    }

    public UserInputMovementCommand(Input input) {
        this.input = input;
    }
}
