package ru.mipt.bit.platformer.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.core.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class TankDrawable implements Drawable {
    private final Tank tank;
    private final Texture texture;
    private final TextureRegion textureRegion;
    private final TileMovement tileMovement;
    private Rectangle rectangle;

    public TankDrawable(Tank tank, Texture texture, TileMovement tileMovement) {
        this.tank = tank;
        this.texture = texture;
        this.textureRegion = new TextureRegion(texture);
        this.tileMovement = tileMovement;
        this.rectangle = createBoundingRectangle(textureRegion);
    }

    @Override
    public void drawTexture(Batch batch) {
        rectangle = tileMovement.moveRectangleBetweenTileCenters(rectangle, tank.getCoordinates(),
                tank.getPlayerDestinationCoordinates(), tank.getPlayerMovementProgress());

        drawTextureRegionUnscaled(batch, textureRegion, rectangle, tank.getPlayerRotation());
    }
}
