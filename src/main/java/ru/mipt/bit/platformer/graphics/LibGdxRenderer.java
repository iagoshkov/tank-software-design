package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.mipt.bit.platformer.util.GdxGameUtils;

public class LibGdxRenderer implements Renderer {

    private final Batch batch;

    public LibGdxRenderer(Batch batch) {
        this.batch = batch;
    }

    @Override
    public void render(Renderable renderable, TextureRegion textureRegion) {
        GdxGameUtils.drawTextureRegionUnscaled(
                batch,
                textureRegion,
                renderable.getRectangle(),
                renderable.getRotation()
        );
    }
}
