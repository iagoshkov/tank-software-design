package ru.mipt.bit.platformer.movement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.models.Player;

import java.util.List;

public class PlayerMovement implements InputHandler {

    private final Player player;
    private final List<GridPoint2> obstacles;

    public PlayerMovement(Player player, List<GridPoint2> obstacles) {
        this.player = player;
        this.obstacles = obstacles;
    }

    @Override
    public void handleInput(float deltaTime) {
        if (player.isMoving()) return;

        GridPoint2 current = player.getCoordinates();
        GridPoint2 target = new GridPoint2(current);

        float rotation = player.getRotation();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            target.add(0, 1);
            rotation = 90f;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            target.add(0, -1);
            rotation = 270f;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            target.add(-1, 0);
            rotation = 180f;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            target.add(1, 0);
            rotation = 0f;
        }

        if (target.equals(current)) return;


        if (!obstacles.contains(target)) {
            player.startMovement(target, rotation);
        } else {
            player.startMovement(current, rotation);
        }
    }
}
