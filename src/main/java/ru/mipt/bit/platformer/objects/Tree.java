package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import java.util.Objects;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tree extends GameObjectAbt implements Drawable, GameObject {

    private Texture texture;
    private TextureRegion graphics;
    private Rectangle rectangle;
    private static Character drawableCharacter = 'T';

    public Tree
            (
                    Texture greenTreeTexture,
                    GridPoint2 coordinates,
                    TiledMapTileLayer groundLayer,
                    Character drawableCharacter
            )
    {
        super(coordinates, 0f);
        this.texture = greenTreeTexture;
        this.graphics = new TextureRegion(greenTreeTexture);
        this.rectangle = createBoundingRectangle(graphics);
        this.drawableCharacter = drawableCharacter;
        this.placeOnLayer(groundLayer);
    }

    public Tree
            (
                    Texture greenTreeTexture,
                    GridPoint2 coordinates,
                    TiledMapTileLayer groundLayer
            )
    {
        this(greenTreeTexture, coordinates, groundLayer, 'T');
    }

    public void placeOnLayer(TiledMapTileLayer groundLayer) {
        moveRectangleAtTileCenter(groundLayer, rectangle, coordinates);
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
}
