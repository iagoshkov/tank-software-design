package ru.mipt.bit.platformer.models.graphics.basic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.models.graphics.Tank;

final public class GameGraphicObjectFactory {
    public static GameGraphicObject createGameGraphicObject(Texture texture, GridPoint2 coordinates) {
        return new GameGraphicObject(texture, coordinates);
    }

    public static Tank createTank(Texture texture, GridPoint2 coordinates, float rotation) {
        return new Tank(texture, coordinates, rotation);
    }
}
