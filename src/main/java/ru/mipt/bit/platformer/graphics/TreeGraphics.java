package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TreeGraphics {

    private final Renderable tree;
    private final TextureRegion graphics;
    private final Renderer renderer;

    public TreeGraphics(Renderable tree, Texture texture, Renderer renderer) {
        this.tree = tree;
        this.graphics = new TextureRegion(texture);
        this.renderer = renderer;
    }

    public void render() {
        renderer.render(tree, graphics);
    }
}
