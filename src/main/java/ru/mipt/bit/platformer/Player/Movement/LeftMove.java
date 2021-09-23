package ru.mipt.bit.platformer.Player.Movement;

import ru.mipt.bit.platformer.Player.Tank;

import com.badlogic.gdx.Gdx;

import static com.badlogic.gdx.Input.Keys.Left;
import static com.badlogic.gdx.Input.Keys.A;
import static ru.mipt.bit.platformer.util.GdxGameUtils.decrementedX;

public class LeftMove implements Move {
    private static Tank tank;
    static float playerMovementProgress1;

    public LeftMove(Tank tank, float playerMovementProgress1) {
        this.tank = tank;
        this.playerMovementProgress1 = playerMovementProgress1;
    }

    public LeftMove(Tank tank) {
        this.tank = tank;
    }

    @Override
    public static void makeMove() {
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            if (tank.CheckMovementProgres(playerMovementProgress1)) {
                if (!treeObstacleCoordinates.equals(decrementedX(tank.getPlayerCoordinates()))) {
                    tank.setPlayerDestinationCoordinates(tank.getPlayerDestinationCoordinates().x--);
                    tank.setPlayerMovementProgress(0f);
                }
                tank.setPlayerRotation(-180f);
            }
        }
    }

}
