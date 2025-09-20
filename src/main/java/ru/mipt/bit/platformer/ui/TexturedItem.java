package ru.mipt.bit.platformer.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class TexturedItem implements Disposable {
    private final Texture texture;
    private final TextureRegion region;
    private final Rectangle rectangle;
    private final GridPoint2 coordinates;
    private final float rotation;


    public TexturedItem(String src, GridPoint2 coordinates) {
        texture = new Texture(src);
        this.coordinates = coordinates;
        region = new TextureRegion(texture);
        rectangle = createBoundingRectangle(region);
        rotation = 0;
    }

    TexturedItem(Texture texture, TextureRegion region, Rectangle rectangle, GridPoint2 coordinates, float rotation) {
        this.texture = texture;
        this.region = region;
        this.rectangle = rectangle;
        this.coordinates = coordinates;
        this.rotation = rotation;
    }

    public TexturedItem rotate(float rotation) {
        return new TexturedItem(texture, region, rectangle, coordinates, rotation);
    }

    public void draw(Batch batch) {
        int regionWidth = region.getRegionWidth();
        int regionHeight = region.getRegionHeight();
        float regionOriginX = regionWidth / 2f;
        float regionOriginY = regionHeight / 2f;
        batch.draw(region, rectangle.x, rectangle.y, regionOriginX, regionOriginY, regionWidth, regionHeight, 1f, 1f, rotation);
    }

    /** {@inheritDoc} */
    @Override
    public void dispose() {
        texture.dispose();
    }
}
