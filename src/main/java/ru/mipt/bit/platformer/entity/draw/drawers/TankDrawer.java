package ru.mipt.bit.platformer.entity.draw.drawers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.entity.draw.base.GameObjectGraphic;
import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class TankDrawer implements GameObjectGraphic  {
    private final Texture texture;
    private final TextureRegion textureRegion;
    private final Rectangle rectangle;
    private final TileMovement tileMovement;
    private final Tank tank;

    public TankDrawer(String imagePath, Tank tank, TileMovement tileMovement) {
        this.texture = new Texture(imagePath);
        this.textureRegion = new TextureRegion(this.texture);
        this.rectangle = createBoundingRectangle(this.textureRegion);
        this.tileMovement = tileMovement;
        this.tank = tank;
    }

    @Override
    public void draw(Batch batch) {
        tileMovement.moveRectangleBetweenTileCenters(
                rectangle,
                tank.getCoordinates(),
                tank.getDestinationCoordinates(),
                tank.getMovementProgress()
        );
        GdxGameUtils.drawTextureRegionUnscaled(batch, textureRegion, rectangle, tank.getDirection().getDirectionRotation());
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
