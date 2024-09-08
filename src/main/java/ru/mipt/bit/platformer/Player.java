package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Player {
    private Texture texture;
    private TextureRegion graphics;
    private Rectangle rectangle;
    private GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private float movementProgress;
    private float rotation;


    public Player(String textureFileName) {
        texture = new Texture(textureFileName);
        graphics = new TextureRegion(texture);
        rectangle = createBoundingRectangle(graphics);
        destinationCoordinates = new GridPoint2(1, 1);
        coordinates = new GridPoint2(destinationCoordinates);
        rotation = 0f;
        movementProgress = 1f;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public TextureRegion getGraphics() {
        return graphics;
    }

    public void setGraphics(TextureRegion graphics) {
        this.graphics = graphics;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(GridPoint2 coordinates) {
        this.coordinates = coordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public void setDestinationCoordinates(GridPoint2 destinationCoordinates) {
        this.destinationCoordinates = destinationCoordinates;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public void setMovementProgress(float movementProgress) {
        this.movementProgress = movementProgress;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void moveUp(MapRendering mapRendering) {
        if (isEqual(movementProgress, 1f)) {
            // check potential player destination for collision with obstacles
            if (!mapRendering.getObjectObstacleCoordinates().equals(incrementedY(this.coordinates))) {
                this.destinationCoordinates.y++;
                setMovementProgress(0f);
            }
            setRotation(90f);
        }
    }

    public void moveDown(MapRendering mapRendering) {
        if (isEqual(movementProgress, 1f)) {
            // check potential player destination for collision with obstacles
            if (!mapRendering.getObjectObstacleCoordinates().equals(decrementedY(this.coordinates))) {
                this.destinationCoordinates.y--;
                setMovementProgress(0f);
            }
            setRotation(-90f);
        }
    }

    public void moveLeft(MapRendering mapRendering) {
        if (isEqual(movementProgress, 1f)) {
            // check potential player destination for collision with obstacles
            if (!mapRendering.getObjectObstacleCoordinates().equals(decrementedX(this.coordinates))) {
                this.destinationCoordinates.x--;
                setMovementProgress(0f);
            }
            setRotation(-180f);
        }
    }

    public void moveRight(MapRendering mapRendering) {
        if (isEqual(movementProgress, 1f)) {
            // check potential player destination for collision with obstacles
            if (!mapRendering.getObjectObstacleCoordinates().equals(incrementedX(this.coordinates))) {
                this.destinationCoordinates.x++;
                setMovementProgress(0f);
            }
            setRotation(0f);
        }
    }
}
