package ru.mipt.bit.platformer.entity.draw.drawers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.entity.draw.base.GameObjectGraphic;
import ru.mipt.bit.platformer.entity.objects.Obstacle;
import ru.mipt.bit.platformer.util.GdxGameUtils;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class ObstacleDrawer implements GameObjectGraphic  {
    private final Texture texture;
    private final TextureRegion textureRegion;
    private final Rectangle rectangle;
    private final Obstacle obstacle;

    public ObstacleDrawer(String imagePath, Obstacle obstacle, TiledMapTileLayer groundLayer) {
        this.texture = new Texture(imagePath);
        this.textureRegion = new TextureRegion(this.texture);
        this.rectangle = createBoundingRectangle(this.textureRegion);
        this.obstacle = obstacle;
        GdxGameUtils.moveRectangleAtTileCenter(
                groundLayer,
                rectangle,
                obstacle.getCoordinates()
        );
    }

    @Override
    public void draw(Batch batch) {
        GdxGameUtils.drawTextureRegionUnscaled(batch, textureRegion, rectangle, 0f);
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
