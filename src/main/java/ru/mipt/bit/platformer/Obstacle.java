package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Obstacle implements Renderable, Disposable {
    private final TextureRegion region;
    private final GridPoint2 coordinates;
    private final Rectangle rectangle;
    private final float rotation;

    public Obstacle(Texture texture, GridPoint2 coordinates, float rotation) {
        this.region = new TextureRegion(texture);
        this.coordinates = coordinates;
        this.rectangle = createBoundingRectangle(region);
        this.rotation = rotation;
    }

    @Override
    public TextureRegion getRegion() {
        return region;
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public float getRotation() {
        return rotation;
    }

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    @Override
    public void dispose() {
        region.getTexture().dispose();
    }
}
