package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class Tree {
    private final Texture texture;
    private final TextureRegion region;
    private final GridPoint2 coordinates;
    private final Rectangle rectangle;

    public Tree(Texture greenTreeTexture, GridPoint2 treeObstacleCoordinates) {
        this.texture = greenTreeTexture;
        this.region = new TextureRegion(this.texture);
        this.coordinates = treeObstacleCoordinates;
        this.rectangle = createBoundingRectangle(this.region);
    }

    public TextureRegion getRegion() {
        return region;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void dispose() {
        this.texture.dispose();
    }
}