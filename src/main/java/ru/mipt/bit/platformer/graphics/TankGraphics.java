package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.abstractions.Tank;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class TankGraphics implements DrawInterface {

    private Rectangle rectangle;

    private final Tank tank;

    private final Texture texture;
    private final TextureRegion textureRegion;

    private final TileMovement tileMovement;

    public TankGraphics(Tank tank, Texture texture, TileMovement tileMovement) {
        this.tank = tank;
        this.texture = texture;
	    // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture
        this.textureRegion = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(textureRegion);
        this.tileMovement = tileMovement;
    }

    // calculate interpolated Tank screen coordinates
    @Override
    public void drawMove() {
        rectangle = tileMovement.moveRectangleBetweenTileCenters(rectangle, tank.getPlayerCoordinates(),
                tank.getPlayerDestinationCoordinates(), tank.getPlayerMovementProgress());
    }

    // render Tank
    @Override
    public void drawTexture(Batch batch) {
        drawTextureRegionUnscaled(batch, textureRegion, rectangle, tank.getPlayerRotation());
    }

}
