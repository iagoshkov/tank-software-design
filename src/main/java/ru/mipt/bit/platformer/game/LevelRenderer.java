package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class LevelRenderer {
    /*
    Класс, отвечающий за отрисовку и обновление уровня (карты).
     */

    private final MapRenderer renderer;
    private final Level level;
    private final Batch batch;
    private final TileMovement tileMovement;
    private final List<LevelObject> levelObjects;

    public LevelRenderer(Level level, Batch batch, List<LevelObject> levelObjects) {
        this.level = level;
        this.batch = batch;
        this.renderer = createSingleLayerMapRenderer(level.getLevelObject(), batch);
        this.tileMovement = new TileMovement(level.getGroundLayer(), Interpolation.smooth);

        this.levelObjects = levelObjects;
        for (LevelObject object : levelObjects) {
            moveRectangleAtTileCenter(level.getGroundLayer(), object.getRectangle(), object.getCoordinates());
        }
    }

    public void clear() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    public float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }

    public void render() {
        renderer.render();

        // start recording all drawing commands
        batch.begin();

        for (LevelObject object : levelObjects) {
            drawTextureRegionUnscaled(batch, object.getGraphics(), object.getRectangle(), object.getRotation());
        }

        // submit all drawing requests
        batch.end();
    }

    public void shiftEntity(LevelObject levelObject, GridPoint2 destination, float progress) {
        tileMovement.moveRectangleBetweenTileCenters(
                levelObject.getRectangle(), levelObject.getCoordinates(), destination, progress
        );
    }

    public void dispose() {
        level.dispose();
        batch.dispose();

        for (LevelObject object : levelObjects)
            object.dispose();
    }
}
