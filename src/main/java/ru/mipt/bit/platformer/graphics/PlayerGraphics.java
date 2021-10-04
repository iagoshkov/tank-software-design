package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.model.Player;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class PlayerGraphics implements Disposable {
    private final TextureRegion region;
    private final Rectangle rectangle;
    private final Player player;
    private final TileMovement tileMovement;

    public PlayerGraphics(Texture texture, Player player, TileMovement tileMovement) {
        this.region = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(region);
        this.player = player;
        this.tileMovement = tileMovement;
    }

    @Override
    public void dispose() {
        region.getTexture().dispose();
    }

    public void move() {
        tileMovement.moveRectangleBetweenTileCenters(rectangle, player.getCoordinates(),
                player.getDestinationCoordinates(), player.getMovementProgress());
    }

    public void draw(Batch batch) {
        drawTextureRegionUnscaled(batch, region, rectangle, player.getRotation());
    }
}
