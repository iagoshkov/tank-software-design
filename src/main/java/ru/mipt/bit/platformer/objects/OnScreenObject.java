package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class OnScreenObject {
    private Texture texture;
    private TextureRegion graphics;
    protected Rectangle rectangle;
    protected GridPoint2 coordinates;

    private void setCoordinates(int[] coordinates) {
        this.coordinates = new GridPoint2(coordinates[0], coordinates[1]);
    }

    private void setTexture(String path) {
        this.texture = new Texture(path);
        this.graphics = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(this.graphics);
    }


    public OnScreenObject (String path, int[] coordinates) {
        setTexture(path);
        setCoordinates(coordinates);
    }

    public TextureRegion getGraphics() {
        return this.graphics;
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
