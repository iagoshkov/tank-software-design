package ru.mipt.bit.platformer.Player.Movement;

import ru.mipt.bit.platformer.Player.Tank;

import com.badlogic.gdx.Gdx;

import static com.badlogic.gdx.Input.Keys.Down;
import static com.badlogic.gdx.Input.Keys.S;
import static ru.mipt.bit.platformer.util.GdxGameUtils.decrementedY;

public class DownMove implements Move {
    private static Tank tank;
    static float playerMovementProgress1;

    public DownMove(Tank tank, float playerMovementProgress1) {
        this.tank = tank;
        this.playerMovementProgress1 = playerMovementProgress1;
    }

    @Override
    public static void makeMove(){
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            if (tank.CheckMovementProgres(playerMovementProgress1)) {
                if (!treeObstacleCoordinates.equals(decrementedY(tank.getPlayerCoordinates()))) {
                    tank.setPlayerDestinationCoordinates(tank.getPlayerDestinationCoordinates().y--);
                    tank.setPlayerMovementProgress(0f);
                }
                tank.setPlayerRotation(-90f);
            }
        }
    }
}
