package ru.mipt.bit.platformer.render;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.PlayerModel;

public class PlayerRender {
    private final TextureRegion playerTexture;
    private final Rectangle playerRectangle;

    public PlayerRender(TextureRegion playerTexture) {
        this.playerTexture = playerTexture;
        this.playerRectangle = new Rectangle(playerTexture.getRegionWidth(), playerTexture.getRegionHeight());
    }

    public void render(Batch batch, PlayerModel playerModel, TiledMapTileLayer groundLayer) {
        moveRectangleBetweenTileCenters(
                groundLayer,
                playerRectangle,
                playerModel.getCoordinates(),
                playerModel.getDestination(),
                playerModel.getMovementProgress()
        );
        drawTextureRegionUnscaled(batch, playerTexture, playerRectangle, playerModel.getRotation());
    }
}
