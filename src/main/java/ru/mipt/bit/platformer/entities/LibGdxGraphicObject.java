package ru.mipt.bit.platformer.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import lombok.Getter;
import ru.mipt.bit.platformer.service.Colliding;
import ru.mipt.bit.platformer.service.Disposable;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;


@Getter
public class LibGdxGraphicObject implements Disposable, Colliding {

    private final Texture texture;
    private final TextureRegion textureRegion;
    private final TiledMapTileLayer tileLayer;
    private final Rectangle rectangle;
    private final LogicObject logicObject;

    public LibGdxGraphicObject(TiledMapTileLayer tileLayer, Texture texture, LogicObject logicObject) {
        this.texture = texture;
        this.textureRegion = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(textureRegion);
        this.logicObject = logicObject;
        this.tileLayer = tileLayer;
        moveRectangleAtTileCenter(tileLayer, rectangle, logicObject.getCoordinates());
    }

    public LibGdxGraphicObject(TiledMapTileLayer tileLayer, Texture texture, GridPoint2 coordinates, float rotation) {
        this(tileLayer, texture, new LogicObject(rotation, coordinates));
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    @Override
    public boolean isCollisionPossible(GridPoint2 othersCoordinates) {
        return !logicObject.getCoordinates().equals(othersCoordinates);
    }
}
