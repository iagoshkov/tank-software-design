package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.abstractions.Tree;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class TreeGraphics implements DrawInterface {
    private final Rectangle rectangle;

    private final Tree tree;

    private final Texture texture;
    private final TextureRegion textureRegion;

    private final TileMovement tileMovement;

    public TreeGraphics(Tree tree, Texture texture, TileMovement tileMovement) {
        this.tree = tree;
        this.texture = texture;
        this.textureRegion = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(textureRegion);
        this.tileMovement = tileMovement;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public void drawMove() {

    }

    // render tree obstacle
    @Override
    public void drawTexture(Batch batch) {
        drawTextureRegionUnscaled(batch, textureRegion, rectangle, tree.getRotation());
    }

}
