package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.mipt.bit.platformer.models.Player;
import ru.mipt.bit.platformer.util.GdxGameUtils;

public class PlayerGraphics {

    private final Player player;
    private final TextureRegion graphics;

    public PlayerGraphics(Player player, Texture texture) {
        this.player = player;
        this.graphics = new TextureRegion(texture);
    }

    public void render(Batch batch) {
        GdxGameUtils.drawTextureRegionUnscaled(
                batch,
                graphics,
                player.getRectangle(),
                player.getRotation()
        );
    }
}
