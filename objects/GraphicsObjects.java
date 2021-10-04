package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class GraphicsObjects {
    private static Batch batch;
    private static Tree tree;

    public GraphicsObjects(Batch batch,Tree tree) {
        this.batch = batch;
        this.tree = tree;
    }

    // Texture decodes an image file and loads it into GPU memory, it represents a native resource
    private static Texture greenTreeTexture = new Texture("images/greenTree.png");
    // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture
    private static final TextureRegion obstacle = new TextureRegion(greenTreeTexture);


    public static TextureRegion getObstacle() {
        return obstacle;
    }
    public static void DrawTree() {
        drawTextureRegionUnscaled(batch, obstacle, tree.getObstacleRectangle(), 0f);
    }

}
