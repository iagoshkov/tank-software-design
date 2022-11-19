package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import ru.mipt.bit.platformer.objects.MovableObject;
import ru.mipt.bit.platformer.objects.OnScreenObject;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static com.badlogic.gdx.Input.Keys.L;
import static java.lang.Math.max;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameGraphics {
    private float legendToggleTimePassed = 1f;
    public void toggleLegend() {
        this.displayLegend = !this.displayLegend;
    }

    private boolean displayLegend = false;
    private final Batch batch;
    private final TiledMap level;
    private final MapRenderer levelRenderer;
    private final TileMovement tileMovement;
    TiledMapTileLayer groundLayer;

    private final ArrayList<TextureRegion> progressBarsGraphics = new ArrayList<>(10);
    private final ArrayList<Rectangle> progressBarsRectangles = new ArrayList<>(10);
    private void initializeProgressBars(String pathToFolder) {
        for (int i = 1; i <= 10; ++i) {
            var texture = new Texture(pathToFolder + i + ".png");
            var graphics = new TextureRegion(texture);
            var rectangle = createBoundingRectangle(graphics);
            rectangle.setHeight(rectangle.getHeight() - 100);
            progressBarsGraphics.add(graphics);
            progressBarsRectangles.add(rectangle);
        }
    }

    public int getFieldWidth() {
        return fieldWidth;
    }
    public int getFieldHeight() {
        return fieldHeight;
    }

    private final int fieldWidth, fieldHeight;
    private final HashSet<OnScreenObject> drawableObjects = new HashSet<>();

    public void addDrawableObjects(Collection<? extends OnScreenObject> objects) {
        for (var object : objects) {
            addDrawableObject(object);
        }
    }

    public void addDrawableObject(OnScreenObject object) {
        drawableObjects.add(object);
        moveRectangleAtTileCenter(groundLayer, object.getObjectGraphics().getRectangle(), object.getCoordinates());
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

        initializeProgressBars("images/progressbar/");
    }

    private void drawObjectsWithLives(OnScreenObject o) {
        o.draw(batch);
        if (o instanceof Tank && displayLegend) {
            Tank tank = (Tank) o;
            int livesScaled1to10 = max(0, tank.getHealth() * 10 / tank.getMaxHealth() - 1);
            var rect = progressBarsRectangles.get(livesScaled1to10);
            tileMovement.moveRectangleBetweenTileCenters(rect, tank.getCoordinates(),
                    tank.getDestinationCoordinates(), tank.getMovementProgress());
            drawTextureRegionUnscaled(batch, progressBarsGraphics.get(livesScaled1to10),
                    rect, 0);
        }
    }

    public void updateScreen(float deltaTime) {
        listenToUserCommand(deltaTime);
        levelRenderer.render();
        batch.begin();
        for (var o : this.drawableObjects) {
            if (o instanceof MovableObject) {
                MovableObject movable = (MovableObject) o;
                tileMovement.moveRectangleBetweenTileCenters(movable.getObjectGraphics().getRectangle(), movable.getCoordinates(),
                        movable.getDestinationCoordinates(), movable.getMovementProgress());
            }
            drawObjectsWithLives(o);
        }
        batch.end();
    }

    private void listenToUserCommand(float deltaTime) {
        legendToggleTimePassed = continueProgress(legendToggleTimePassed, deltaTime, 1f);
        if (legendToggleTimePassed >= 0.3f) {
            var input = Gdx.input;
            if (input.isKeyPressed(L)) {
                legendToggleTimePassed = 0;
                toggleLegend();
            }

        }
    }

    public void disposeAllDrawableObjects() {
        for (var o : drawableObjects) {
            o.getObjectGraphics().dispose();
        }
        level.dispose();
        batch.dispose();
    }
}
