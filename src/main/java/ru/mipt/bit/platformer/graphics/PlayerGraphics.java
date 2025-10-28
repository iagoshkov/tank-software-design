package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerGraphics {

    private final Renderable player;
    private final TextureRegion graphics;
    private final Renderer renderer;

    public PlayerGraphics(Renderable player, Texture texture, Renderer renderer) {
        this.player = player;
        this.graphics = new TextureRegion(texture);
        this.renderer = renderer;
    }

    public void render() {
        renderer.render(player, graphics);
    }
}
