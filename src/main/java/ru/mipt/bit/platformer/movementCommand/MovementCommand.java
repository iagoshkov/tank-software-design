package ru.mipt.bit.platformer.movementCommand;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.OnScreenObject;
import ru.mipt.bit.platformer.objects.Tank;

import java.util.ArrayList;
import java.util.Map;

public interface MovementCommand {
//    public GridPoint2 getNewPlayerCoordinates();
    Map<Tank, TankAction> getTankActions(ArrayList<OnScreenObject> obstacles, ArrayList<Tank> tanks, int fieldWidth, int fieldHeight);
}
