package ru.mipt.bit.platformer.Player.Movement;

import ru.mipt.bit.platformer.Player.Tank;

import com.badlogic.gdx.Gdx;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.decrementedY;

public class UpMove {
    private static Tank tank;
    static float playerMovementProgress1;

    public UpMove(Tank tank, float playerMovementProgress1) {
        this.tank = tank;
        this.playerMovementProgress1 = playerMovementProgress1;
    }
    @Override
    public static void makeMove(){
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            if (tank.CheckMovementProgres(playerMovementProgress1)) {
                // check potential player destination for collision with obstacles
                if (!treeObstacleCoordinates.equals(incrementedY(tank.getPlayerCoordinates()))) {
                    tank.setPlayerCoordinates(tank.getPlayerDestinationCoordinates().y++);
                    tank.setPlayerMovementProgress(0f);
                }
                tank.setPlayerRotation(90f);
            }
        }
    }
}
