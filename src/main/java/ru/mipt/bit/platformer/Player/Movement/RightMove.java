package ru.mipt.bit.platformer.Player.Movement;

import ru.mipt.bit.platformer.Player.Tank;

import com.badlogic.gdx.Gdx;

import static com.badlogic.gdx.Input.Keys.Right;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.decrementedX;

public class RightMove {
    private static Tank tank;
    static float playerMovementProgress1;

    public RightMove(Tank tank, float playerMovementProgress1) {
        this.tank = tank;
        this.playerMovementProgress1 = playerMovementProgress1;
    }
    @Override
    public static void makeMove(){
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            if (tank.CheckMovementProgres(playerMovementProgress1)) {
                if (!treeObstacleCoordinates.equals(incrementedX(tank.getPlayerCoordinates()))) {
                    tank.setPlayerCoordinates(tank.getPlayerDestinationCoordinates().x++);
                    tank.setPlayerMovementProgress(0f);
                }
                tank.setPlayerRotation(0f);
            }
        }
    }
}
