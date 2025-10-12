package ru.mipt.bit.platformer.movement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class GdxInputController implements InputController {

    @Override
    public boolean isUpPressed() { return Gdx.input.isKeyPressed(Input.Keys.W); }

    @Override
    public boolean isDownPressed() { return Gdx.input.isKeyPressed(Input.Keys.S); }

    @Override
    public boolean isLeftPressed() { return Gdx.input.isKeyPressed(Input.Keys.A); }

    @Override
    public boolean isRightPressed() { return Gdx.input.isKeyPressed(Input.Keys.D); }
}
