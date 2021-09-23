package ru.mipt.bit.platformer.models.graphics.basic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class GameGraphicObject implements Disposable {
    protected final Texture texture;
    protected final TextureRegion textureRegion;
    protected final Rectangle rectangle;
    protected float rotation;
    protected GridPoint2 coordinates;


    public GameGraphicObject(Texture texture, GridPoint2 coordinates) {
        this.texture = texture;
        this.textureRegion = new TextureRegion(texture);
        this.coordinates = coordinates;
        this.rectangle = createBoundingRectangle(textureRegion);
    }

    public GameGraphicObject(Texture texture, GridPoint2 coordinates, float rotation) {
        this.texture = texture;
        this.rotation = rotation;
        this.coordinates = coordinates;
        this.textureRegion = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(textureRegion);
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Texture getTexture() {
        return texture;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public float getRotation() {
        return rotation;
    }

    public void setCoordinates(GridPoint2 coordinates) {
        this.coordinates = coordinates;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
