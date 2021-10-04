package ru.mipt.bit.platformer.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.mipt.bit.platformer.player.move.NewDestination;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class GraphicsPlayer {

    private static Batch batch;
    private static Player tank;
    private static TileMovement tileMovement;
    // Texture decodes an image file and loads it into GPU memory, it represents a native resource
    private static Texture blueTankTexture = new Texture("images/tank_blue.png");
    // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture
    private static final TextureRegion player = new TextureRegion(blueTankTexture);

    public GraphicsPlayer(Player tank, Batch batch, TileMovement tileMovement) {
        this.tank = tank;
        this.batch = batch;
        this.tileMovement = tileMovement;
    }

    public static TextureRegion getPlayer() {
        return player;
    }

    public static void CalcInterpPlayerCoordinate() {
        tileMovement.moveRectangleBetweenTileCenters(tank.getRectangle(), tank.getCoordinates(), NewDestination.getDestinationCoordinates(), tank.getMovementProgress());
    }

    public static void DrawPlayer() {
        drawTextureRegionUnscaled(batch, player, tank.getRectangle(), tank.getRotation());
    }
}
