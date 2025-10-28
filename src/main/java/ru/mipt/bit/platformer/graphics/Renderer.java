package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface Renderer {
    void render(Renderable renderable, TextureRegion textureRegion);
}
