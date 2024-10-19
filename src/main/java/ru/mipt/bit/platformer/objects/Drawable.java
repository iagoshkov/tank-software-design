package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public interface Drawable extends GameObject {
    Texture getTexture();

    void setTexture(Texture texture);

    TextureRegion getGraphics();

    void setGraphics(TextureRegion graphics);

    Rectangle getRectangle();

    void setRectangle(Rectangle rectangle);

    Character getDrawableCharacter();

    void setDrawableCharacter(Character character);

    void dispose();

    void draw(Batch batch);
}
