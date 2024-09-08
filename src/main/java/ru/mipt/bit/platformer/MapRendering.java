package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class MapRendering {
    private Texture objectTexture;
    private TextureRegion objectObstacleGraphics;
    private GridPoint2 objectObstacleCoordinates;
    private Rectangle objectObstacleRectangle;

    public MapRendering(String objectTextureFileName) {
        objectObstacleCoordinates = new GridPoint2();
        objectObstacleRectangle = new Rectangle();

        objectTexture = new Texture(objectTextureFileName);
        objectObstacleGraphics = new TextureRegion(objectTexture);
        objectObstacleCoordinates = new GridPoint2(1, 3);
        objectObstacleRectangle = createBoundingRectangle(objectObstacleGraphics);
    }

    public Texture getObjectTexture() {
        return objectTexture;
    }

    public void setObjectTexture(Texture objectTexture) {
        this.objectTexture = objectTexture;
    }

    public TextureRegion getObjectObstacleGraphics() {
        return objectObstacleGraphics;
    }

    public void setObjectObstacleGraphics(TextureRegion objectObstacleGraphics) {
        this.objectObstacleGraphics = objectObstacleGraphics;
    }

    public GridPoint2 getObjectObstacleCoordinates() {
        return objectObstacleCoordinates;
    }

    public void setObjectObstacleCoordinates(GridPoint2 objectObstacleCoordinates) {
        this.objectObstacleCoordinates = objectObstacleCoordinates;
    }

    public Rectangle getObjectObstacleRectangle() {
        return objectObstacleRectangle;
    }

    public void setObjectObstacleRectangle(Rectangle objectObstacleRectangle) {
        this.objectObstacleRectangle = objectObstacleRectangle;
    }
}
