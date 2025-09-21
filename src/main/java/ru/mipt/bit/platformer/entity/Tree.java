package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;

public class Tree {
    private GridPoint2 coordinates;
    private Texture texture;

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setCoordinates(GridPoint2 coordinates) {
        this.coordinates = coordinates;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
