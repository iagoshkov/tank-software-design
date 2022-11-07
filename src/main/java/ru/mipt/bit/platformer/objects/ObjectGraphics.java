package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class ObjectGraphics {
    private final TextureRegion graphics;
    private final Texture texture;

    public TextureRegion getGraphics() {
        return graphics;
    }

    public Texture getTexture() {
        return texture;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    protected Rectangle rectangle;


    public ObjectGraphics(TextureRegion graphics, Texture texture, Rectangle rectangle) {
        this.graphics = graphics;
        this.texture = texture;
        this.rectangle = rectangle;
    }

    public ObjectGraphics(String path) {
        this.texture = new Texture(path);
        this.graphics = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(this.graphics);
    }

    public void draw(Batch batch, float rotation) {
        drawTextureRegionUnscaled(batch, this.getGraphics(), this.getRectangle(), rotation);

    }

    public void dispose() {
        texture.dispose();
    }
}
