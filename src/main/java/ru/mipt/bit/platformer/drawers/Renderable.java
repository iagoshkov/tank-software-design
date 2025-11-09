package ru.mipt.bit.platformer.drawers;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface Renderable {
    void draw(Batch batch);
    void dispose();
}