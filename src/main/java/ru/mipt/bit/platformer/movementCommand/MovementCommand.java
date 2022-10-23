package ru.mipt.bit.platformer.movementCommand;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.OnScreenObject;
import ru.mipt.bit.platformer.objects.Player;

import java.util.ArrayList;
import java.util.Map;

public interface MovementCommand {
//    public GridPoint2 getNewPlayerCoordinates();
    Map<Player, GridPoint2> getTankActions(ArrayList<OnScreenObject> obstacles, ArrayList<Player> players, int fieldWidth, int fieldHeight);
}
