package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.input.InputController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.model.Direction;

import java.util.Optional;

public final class GdxKeyboardInputController implements InputController {
    
    @Override
    public Optional<Direction> pollMove() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            return Optional.of(Direction.UP);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            return Optional.of(Direction.DOWN);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            return Optional.of(Direction.LEFT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            return Optional.of(Direction.RIGHT);
        }
        return Optional.empty();
    }
}
