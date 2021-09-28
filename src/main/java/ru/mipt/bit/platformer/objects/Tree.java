package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tree {
    private final TextureRegion obstacleGraphics;
    private final Rectangle obstacleRectangle;
    private final GridPoint2 obstacleCoordinates = new GridPoint2(1, 3);

    public Tree(Texture greenTreeTexture) {
        this.obstacleGraphics = new TextureRegion(greenTreeTexture);
        this.obstacleRectangle = createBoundingRectangle(obstacleGraphics);
    }


    public GridPoint2 getObstacleCoordinates() {
        return obstacleCoordinates;
    }

    public TextureRegion getObstacleGraphics() {
        return obstacleGraphics;
    }

    public Rectangle getObstacleRectangle() {
        return obstacleRectangle;
    }


}
