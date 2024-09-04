package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.Gdx;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;

public class UserInput {
    /*
    Класс, считывающий нажатие игроком клавиш клавиатуры.
     */

    public static PlayerMove handleUserInput() {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W))
            return PlayerMove.UP;
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A))
            return PlayerMove.LEFT;
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S))
            return PlayerMove.DOWN;
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D))
            return PlayerMove.RIGHT;
        return null;
    }

}
