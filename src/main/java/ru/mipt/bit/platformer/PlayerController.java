package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Player;

import java.util.List;

public class PlayerController {
    private final Player player;
    private final List<Obstacle> obstacles;

    public PlayerController(Player player, List<Obstacle> obstacles) {
        this.player = player;
        this.obstacles = obstacles;
    }

    private static Direction calcDirectionFromInputKey(int key) {
        if (key == Input.Keys.RIGHT || key == Input.Keys.D) {
            return Direction.RIGHT;
        }
        if (key == Input.Keys.UP || key == Input.Keys.W) {
            return Direction.UP;
        }
        if (key == Input.Keys.LEFT || key == Input.Keys.A) {
            return Direction.LEFT;
        }
        if (key == Input.Keys.DOWN || key == Input.Keys.S) {
            return Direction.DOWN;
        }
        throw new IllegalArgumentException();
    }

    public void process() {
        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        Integer key = InputProcessor.getPressedKey();
        if (key != null) {
            player.tryRotateAndStartMovement(calcDirectionFromInputKey(key), obstacles);
        }

        player.makeProgress(deltaTime);
        player.tryFinishMovement();
    }
}
