package ru.mipt.bit.platformer.util.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class BulletGraphics {

    private final Texture bulletTexture;
    private final TextureRegion graphics;
    private final Rectangle rectangle;

    public BulletGraphics(Texture bulletTexture){
        this.bulletTexture = bulletTexture;
        this.graphics = new TextureRegion(bulletTexture);
        this.rectangle = createBoundingRectangle(this.graphics);
    }

    public TextureRegion getGraphics() {
        return graphics;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Texture getBulletTexture() {
        return bulletTexture;
    }
}
