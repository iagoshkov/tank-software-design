package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;

import ru.mipt.bit.platformer.abstractions.Tank;
import ru.mipt.bit.platformer.abstractions.Tree;

import static com.badlogic.gdx.Input.Keys.*;

public class MoveChecker {

    private final Tank tank;
    private final Tree tree;

    public MoveChecker(Tank tank, Tree tree) {
        this.tank = tank;
        this.tree = tree;
    }

    public void checkMoves() {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            if (tank.isMoving()) {
                tank.moveUp(tree.getCoordinates());
            }
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            if (tank.isMoving()) {
                tank.moveLeft(tree.getCoordinates());
            }
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            if (tank.isMoving()) {
                tank.moveDown(tree.getCoordinates());
            }
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            if (tank.isMoving()) {
                tank.moveRight(tree.getCoordinates());
            }
        }
    }
}
