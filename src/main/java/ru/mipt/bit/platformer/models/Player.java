package ru.mipt.bit.platformer.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.models.graphics.basic.GameGraphicObject;
import ru.mipt.bit.platformer.models.graphics.Direction;
import ru.mipt.bit.platformer.models.graphics.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.List;

import static ru.mipt.bit.platformer.models.graphics.Direction.getCalledDirection;
import static ru.mipt.bit.platformer.util.GdxGameUtils.checkIsMoveSafe;

public class Player {
    private final TileMovement tileMovement;
    private final Tank tank;

    public Player(Tank tank, TileMovement tileMovement) {
        this.tank = tank;
        this.tileMovement = tileMovement;
    }

    public void handleKeyEvent(Input input, List<GameGraphicObject> gameGraphicObjects) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        if (tank.isStopped()) {
            Direction direction = getCalledDirection(input);
            if (checkIsMoveSafe(direction, gameGraphicObjects, tank)) {
                tileMovement.calculateMovableGameObjectCoordinates(tank);
                tank.move(direction, deltaTime);
            }
        }
    }

    public Tank getTank() {
        return tank;
    }
}
