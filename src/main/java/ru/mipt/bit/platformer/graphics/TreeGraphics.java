package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.mipt.bit.platformer.models.Tree;
import ru.mipt.bit.platformer.util.GdxGameUtils;

public class TreeGraphics {
    private final Tree tree;
    private final TextureRegion graphics;

    public TreeGraphics(Tree tree, Texture texture) {
        this.tree = tree;
        this.graphics = new TextureRegion(texture);
    }

    public void render(Batch batch) {
        GdxGameUtils.drawTextureRegionUnscaled(
                batch,
                graphics,
                tree.getRectangle(),
                0f
        );
    }
}
