package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class OnScreenObject {
    protected float rotation = 0f;
    private Texture texture;

    public float getRotation() {
        return rotation;
    }

    public TextureRegion getGraphics() {
        return graphics;
    }

    private TextureRegion graphics;
    protected Rectangle rectangle;
    protected GridPoint2 coordinates;

    private void setTexture(String path) {
        this.texture = new Texture(path);
        this.graphics = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(this.graphics);
    }

    public OnScreenObject (String path, GridPoint2 coordinates) {
        setTexture(path);
        this.coordinates = new GridPoint2(coordinates);
    }

    public GridPoint2 getCoordinates() {
        return this.coordinates;
    }

    public Rectangle getRectangle() {
        return this.rectangle;
    }

    public void dispose() {
        texture.dispose();
    }
}
