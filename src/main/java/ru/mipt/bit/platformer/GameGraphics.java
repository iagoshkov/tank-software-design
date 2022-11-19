package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.objects.OnScreenObject;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameGraphics {
    public void toggleLegend() {
        this.displayLegend = !this.displayLegend;
    }

    private boolean displayLegend = false;
    private final Batch batch;
    private final TiledMap level;
    private final MapRenderer levelRenderer;
    private final TileMovement tileMovement;
    TiledMapTileLayer groundLayer;

    public int getFieldWidth() {
        return fieldWidth;
    }
    public int getFieldHeight() {
        return fieldHeight;
    }

    private int fieldWidth, fieldHeight;
    private final HashSet<OnScreenObject> drawableObjects = new HashSet<>();

    public void addDrawableObjects(Collection<? extends OnScreenObject> objects) {
        for (var object : objects) {
            addDrawableObject(object);
        }
    }

    public void addDrawableObject(OnScreenObject object) {
        this.drawableObjects.add(object);
        moveRectangleAtTileCenter(this.groundLayer, object.getObjectGraphics().getRectangle(), object.getCoordinates());
    }

    public void removeDrawableObjects(Collection<? extends OnScreenObject> objects) {
        for (var object : objects) {
            removeDrawableObject(object);
        }
    }

    public void removeDrawableObject(OnScreenObject object) {
        this.drawableObjects.remove(object);
    }

    public GameGraphics() {
        this.batch = new SpriteBatch();
        this.level = new TmxMapLoader().load("level.tmx");
        this.levelRenderer = createSingleLayerMapRenderer(level, batch);
        this.groundLayer = getSingleLayer(level);
        this.tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        this.fieldWidth = groundLayer.getWidth();
        this.fieldHeight = groundLayer.getHeight();
    }

    public void drawAllObjects() {
        levelRenderer.render();
        batch.begin();
        for (var o : this.drawableObjects) {
            if (o instanceof Tank) {
                Tank tank = (Tank) o;
                tileMovement.moveRectangleBetweenTileCenters(tank.getObjectGraphics().getRectangle(), tank.getCoordinates(),
                        tank.getDestinationCoordinates(), tank.getMovementProgress());
            } else if (o instanceof Bullet) {
                Bullet bullet = (Bullet) o;
                tileMovement.moveRectangleBetweenTileCenters(bullet.getObjectGraphics().getRectangle(), bullet.getCoordinates(),
                        bullet.getDestinationCoordinates(), bullet.getMovementProgress());
            }
            o.draw(batch);
        }
        batch.end();
    }

    public void disposeAllDrawableObjects() {
        for (var o : drawableObjects) {
            o.getObjectGraphics().dispose();
        }
        level.dispose();
        batch.dispose();
    }
}
