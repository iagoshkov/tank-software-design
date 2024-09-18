package ru.mipt.bit.platformer.game.controls;

import com.badlogic.gdx.Gdx;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;

public class KeyboardHandler implements InputHandler {
    /*
    Класс, считывающий нажатие игроком клавиш клавиатуры.
     */

    public UserCommand handleUserInput() {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W))
            return MoveCommand.UP;
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A))
            return MoveCommand.LEFT;
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S))
            return MoveCommand.DOWN;
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D))
            return MoveCommand.RIGHT;
        return null;
    }

}
