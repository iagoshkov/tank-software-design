package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class Tank {
    private final Texture texture;
    private final TextureRegion graphics;
    private final Rectangle rectangle;
    public GridPoint2 coordinates; // player current position coordinates
    public GridPoint2 destinationCoordinates; // which tile the player want to go next
    private float movementProgress = 1f;
    private Orientation orientation;
    public Tank(Texture texture, GridPoint2 gridPoint2) {
        this.texture = texture;
        this.destinationCoordinates = gridPoint2;
        this.graphics = new TextureRegion(this.texture);
        this.rectangle = createBoundingRectangle(this.graphics);
        this.coordinates = new GridPoint2(this.destinationCoordinates);
        this.orientation = Orientation.RIGHT;
    }
    
    public TextureRegion getGraphics() {
        return graphics;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public void setMovementProgress(float movementProgress) {
        this.movementProgress = movementProgress;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public void dispose() {
        this.texture.dispose();
    }

}