package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class TreeGraphics {

    // до конца не понял как грамотно отвязать графику от дерева чтоб дерево не зависело от графики

    private final Texture texture;
    private final TextureRegion region;
    private final Rectangle rectangle;

    public TreeGraphics(Texture texture, TextureRegion region, Rectangle rectangle) {
        this.texture = texture;
        this.region = region;
        this.rectangle = rectangle;
    }
}
