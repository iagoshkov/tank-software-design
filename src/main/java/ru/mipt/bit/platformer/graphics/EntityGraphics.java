package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EntityGraphics {

    private final Renderable entity;
    private final TextureRegion texture;
    private final Renderer renderer;

    public EntityGraphics(Renderable entity, Texture texture, Renderer renderer) {
        this.entity = entity;
        this.texture = new TextureRegion(texture);
        this.renderer = renderer;
    }

    public void render() {
        renderer.render(entity, texture);
    }
}
