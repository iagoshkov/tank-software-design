package ru.mipt.bit.platformer.movementCommand;

import ru.mipt.bit.platformer.objects.OnScreenObject;
import ru.mipt.bit.platformer.objects.Tank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public interface MovementCommand {
//    public GridPoint2 getNewPlayerCoordinates();
    Map<Tank, TankAction> getTankActions(Collection<OnScreenObject> obstacles, Collection<Tank> tanks, int fieldWidth, int fieldHeight);
}
