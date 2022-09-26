package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class TankGraphics {

    // до конца не понял как грамотно отвязать графику от танка чтоб танк не зависел от графики

    private final Texture texture;
    private final TextureRegion graphics;
    private final Rectangle rectangle;

    public TankGraphics(Texture texture, TextureRegion graphics, Rectangle rectangle) {
        this.texture = texture;
        this.graphics = graphics;
        this.rectangle = rectangle;
    }
}
