package ru.mipt.bit.platformer.Controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Entities.Tank;
import ru.mipt.bit.platformer.Entities.TankEntity;
import ru.mipt.bit.platformer.Obstacles.IObstacleInLevel;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import static ru.mipt.bit.platformer.util.GdxGameUtils.incrementedX;

public class TankControl implements IControl {
    @Override
    public void control(TankEntity tankEntity, Input input, IObstacleInLevel obstacleInLevel) {
        if(!isEqual(tankEntity.movementProgress, 1f)) return;
        GridPoint2 destinationPosition = tankEntity.position;
        if (input.isKeyPressed(UP) || input.isKeyPressed(W)) {
            destinationPosition = incrementedY(tankEntity.position);
            tankEntity.rotation = 90f;
        }
        if (input.isKeyPressed(LEFT) || input.isKeyPressed(A)) {
            destinationPosition = decrementedX(tankEntity.position);
            tankEntity.rotation = -180f;
        }
        if (input.isKeyPressed(DOWN) || input.isKeyPressed(S)) {
            destinationPosition = decrementedY(tankEntity.position);
            tankEntity.rotation = -90f;
        }
        if (input.isKeyPressed(RIGHT) || input.isKeyPressed(D)) {
            destinationPosition = incrementedX(tankEntity.position);
            tankEntity.rotation = 0f;
        }

        boolean isLetPosition = obstacleInLevel.getObstaclePositions().contains(destinationPosition);
        if (!isLetPosition) {
            tankEntity.destinationPosition = destinationPosition;
            tankEntity.movementProgress = 0f;
        }
    }
}
