package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Interpolation;
import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import java.util.List;

public class RigidBody {
    private Texture texture;
    private TextureRegion graphics;
    private Rectangle rectangle;

    private GridPoint2 coordinates;
    protected float rotation;

    public RigidBody(Texture texture, GridPoint2 startCoordinates) {
        this.texture = texture;
        this.graphics = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(graphics);

        this.coordinates = new GridPoint2(startCoordinates);
        this.rotation = 0f;
    }

    public void dispose() {
        texture.dispose();
    }

    public void update(float deltaTime, List<GridPoint2> obstacleCoordinates) {
        handleMovement(obstacleCoordinates);
    }

    private void handleMovement(List<GridPoint2> obstacleCoordinates) {
    }

    public void render(Batch batch) {
        drawTextureRegionUnscaled(batch, graphics, rectangle, rotation);
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
