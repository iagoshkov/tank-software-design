package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class Player {
    private final TextureRegion graphics;
    private final Rectangle rectangle;
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private GridPoint2 coordinates;
    // which tile the player want to go next
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private float rotation;

    Player(Texture texture) {
        // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture
        this.graphics = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(this.graphics);
        // set player initial position
        this.destinationCoordinates = new GridPoint2(1, 1);
        this.coordinates = new GridPoint2(this.destinationCoordinates);
        this.rotation = 0f;
    }

    public boolean isMoving() {
        return isEqual(this.movementProgress, 1f);
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRotation() {
        return this.rotation;
    }

    public void setMovementProgress(float progress) {
        this.movementProgress = progress;
    }

    public float getMovementProgress() {
        return this.movementProgress;
    }

    public void setCoordinates(GridPoint2 coordinates) {
        this.coordinates = coordinates;
    }

    public GridPoint2 getCoordinates() {
        return this.coordinates;
    }

    public void setDestinationCoordinates(GridPoint2 coordinates) {
        this.destinationCoordinates = coordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return this.destinationCoordinates;
    }

    public TextureRegion getGraphics() {
        return this.graphics;
    }

    public Rectangle getRectangle() {
        return this.rectangle;
    }
}
