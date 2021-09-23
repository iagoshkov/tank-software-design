package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

public interface Renderable {
    TextureRegion getRegion();

    Rectangle getRectangle();

    float getRotation();

    GridPoint2 getCoordinates();
}
