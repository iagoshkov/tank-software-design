package ru.mipt.bit.platformer;  // или в какой пакет вы поместили InputHandler

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Direction;  
import ru.mipt.bit.platformer.objects.Player;
import ru.mipt.bit.platformer.objects.Tree;

import static com.badlogic.gdx.Input.Keys.*;

public class InputHandler {
    private final Player player;
    private final Tree tree;

    public InputHandler(Player player, Tree tree) {
        this.player = player;
        this.tree = tree;
    }

    public void handleInput() {
        if (player.isMoving()) return;

        if (isKeyPressed(UP, W)) {
            tryMove(Direction.UP);
        } else if (isKeyPressed(LEFT, A)) {
            tryMove(Direction.LEFT);
        } else if (isKeyPressed(DOWN, S)) {
            tryMove(Direction.DOWN);
        } else if (isKeyPressed(RIGHT, D)) {
            tryMove(Direction.RIGHT);
        }
    }

    private boolean isKeyPressed(int... keyCodes) {
        for (int keyCode : keyCodes) {
            if (Gdx.input.isKeyPressed(keyCode)) {
                return true;
            }
        }
        return false;
    }

    private void tryMove(Direction direction) {
        GridPoint2 nextPosition = direction.getNextPosition(player.getCoordinates());
        if (player.canMoveTo(nextPosition, tree)) {
            player.move(direction);
        }
    }
}