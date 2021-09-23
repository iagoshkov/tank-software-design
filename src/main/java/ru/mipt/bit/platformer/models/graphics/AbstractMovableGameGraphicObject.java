package ru.mipt.bit.platformer.models.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.models.graphics.basic.GameGraphicObject;

abstract public class AbstractMovableGameGraphicObject extends GameGraphicObject implements Movable {
    protected GridPoint2 destinationCoordinates = new GridPoint2(this.coordinates);
    protected float movementProgress = 0f;

    public AbstractMovableGameGraphicObject(Texture texture, GridPoint2 coordinates) {
        super(texture, coordinates);
    }

    public AbstractMovableGameGraphicObject(Texture texture, GridPoint2 coordinates, float rotation) {
        super(texture, coordinates, rotation);
    }
}
