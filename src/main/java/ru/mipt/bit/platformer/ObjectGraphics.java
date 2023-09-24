package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Graphics {
    private TextureRegion graphics;
    private Rectangle rectangle = new Rectangle();
    private TileMovement tileMovement;
    public Graphics(Texture texture, TileMovement tileMovement)  {
        graphics = new TextureRegion(texture);
        rectangle = createBoundingRectangle(graphics);
        this.tileMovement = tileMovement;
    }
    public void draw(Batch batch, float rotation) {
        drawTextureRegionUnscaled(batch, graphics, rectangle, rotation);
    }

    public void moveRectangle(TiledMapTileLayer groundLayer, GridPoint2 coordinates){
        moveRectangleAtTileCenter(groundLayer, rectangle, coordinates);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
