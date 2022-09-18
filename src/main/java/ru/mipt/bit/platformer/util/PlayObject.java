package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class PlayObject {
    public Texture texture;
    public TextureRegion graphics;
    public Rectangle rectangle;
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    public GridPoint2 coordinates;
    // which tile the player want to go next
    public GridPoint2 initialCoordinates;
    public float movementProgress = 1f;
    public float rotation;

    public PlayObject(Texture texture, GridPoint2 initialCoordinates, float rotation) {
        this(texture, initialCoordinates);
        this.coordinates = new GridPoint2(this.initialCoordinates);
        this.rotation = rotation;
    }
    public PlayObject(Texture texture, GridPoint2 initialCoordinates) {
        this.texture = texture;
        this.graphics = new TextureRegion(this.texture);
        this.rectangle = createBoundingRectangle(this.graphics);
        this.initialCoordinates = initialCoordinates;
    }
}
