package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;


import java.util.Objects;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tank extends Ghost implements Drawable, Movable {

    // Texture decodes an image file and loads it into GPU memory, it represents a native resource
    private Texture texture;
    // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture
    private TextureRegion graphics;
    private Rectangle rectangle;
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    // which tile the player want to go next
    private final TileMovement tileMovement;
    private static Character drawableCharacter = 'X';

    public Tank
            (
                    Texture texture,
                    GridPoint2 coordinates,
                    float movementSpeed,
                    float movementProgress,
                    float rotation,
                    TileMovement tileMovement,
                    Character drawableCharacter
            ) {
        super(coordinates, movementSpeed, movementProgress);
        this.rotation = rotation;
        this.texture = texture;
        this.graphics = new TextureRegion(texture);
        this.tileMovement = tileMovement;
        this.rectangle = createBoundingRectangle(graphics);
        this.drawableCharacter = drawableCharacter;
    }

    public Tank
            (
                    Texture texture,
                    GridPoint2 coordinates,
                    float movementSpeed,
                    float movementProgress,
                    float rotation,
                    TileMovement tileMovement
            ) {
        this(texture, coordinates, movementSpeed, movementProgress, rotation, tileMovement, 'X');
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    @Override
    public TextureRegion getGraphics() {
        return graphics;
    }

    @Override
    public void setGraphics(TextureRegion graphics) {
        this.graphics = graphics;
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    @Override
    public Character getDrawableCharacter() {
        return drawableCharacter;
    }

    public static Character getDrawableCharacterStatic() {
        return drawableCharacter;
    }

    @Override
    public void setDrawableCharacter(Character character) {
        drawableCharacter = character;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    @Override
    public void draw(Batch batch) {
        drawTextureRegionUnscaled(batch, graphics, rectangle, rotation);
    }

    @Override
    public void changeMovementState(float deltaTime) {
        moveRectangle(tileMovement);
        super.changeMovementState(deltaTime);
    }

    private void moveRectangle(TileMovement tileMovement) {
        tileMovement.moveRectangleBetweenTileCenters(rectangle, coordinates, destinationCoordinates, movementProgress);
    }
}
