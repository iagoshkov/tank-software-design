package ru.mipt.bit.platformer;

import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.decrementedX;
import static ru.mipt.bit.platformer.util.GdxGameUtils.decrementedY;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.incrementedX;
import static ru.mipt.bit.platformer.util.GdxGameUtils.incrementedY;
import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;

// import static com.badlogic.gdx.Input.Keys.*;
// import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
// import static com.badlogic.gdx.math.MathUtils.isEqual;
// import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public abstract class GameObject{
    protected TextureRegion graphics;
    protected Rectangle rectangle;
    protected GridPoint2 coordinates;

    public abstract void render(Batch batch);
    public abstract void dispose();
}

public class Tank extends GameObject{
    private float rotation;
    private float movementProgress=1f;
    private GridPoint2 destinationCoordinates;
    private TileMovement tileMovement;

    public Tank(Texture texture, TiledMapTileLayer layer, GridPoint2 initialPos){
        this.graphics = new TextureRegion(texture);
        this.rectangle = GdxGameUtils.createBoundingRectangle(graphics);
        this.coordinates = new GridPoint2(initialPos);
        this.destinationCoordinates = new GridPoint2(initialPos);
        this.tileMovement = new TileMovement(layer, Interpolation.smooth);
        moveToTile(initialPos);
    }
    private void moveToTile(GridPoint2 position) {
        GdxGameUtils.moveRectangleAtTileCenter(tileMovement.getTileLayer(), rectangle, position);
    }
    
    @Override
    public void render(Batch batch) {
        GdxGameUtils.drawTextureRegionUnscaled(batch, graphics, rectangle, rotation);
    }
    
    @Override
    public void dispose() {
        graphics.getTexture().dispose();
    }
    public boolean move(Direction direction, GridPoint2 obstacleCoordinates){
        if(movementProgress < 1f) 
            return false;
        GridPoint2 target = direction.apply(coordinates);
        if(obstacleCoordinates.equals(target)) 
            return false;
        destinationCoordinates.set(target);
        movementProgress = 0f;
        rotation = direction.getRotation();
        return true;
    }

    public void update(float deltaTime, float speed) {
        movementProgress = GdxGameUtils.continueProgress(movementProgress, deltaTime, speed);
        tileMovement.moveRectangleBetweenTileCenters(rectangle, coordinates, destinationCoordinates, movementProgress);
        if (movementProgress >= 1f) {
            coordinates.set(destinationCoordinates);
        }
    }
    public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates);
    }
    
    public TiledMapTileLayer getTileLayer() {
        return tileMovement.getTileLayer();
    }
}

public class Tree extends GameObject {
    public Tree(Texture texture, TiledMapTileLayer layer, GridPoint2 position) {
        this.graphics = new TextureRegion(texture);
        this.rectangle = GdxGameUtils.createBoundingRectangle(graphics);
        this.coordinates = new GridPoint2(position);
        GdxGameUtils.moveRectangleAtTileCenter(layer, rectangle, coordinates);
    }
    @Override
    public void render(Batch batch) {
        GdxGameUtils.drawTextureRegionUnscaled(batch, graphics, rectangle, 0f);
    }
    
    @Override
    public void dispose() {
        graphics.getTexture().dispose();
    }
    
    public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates);
    }
}

public enum Direction {
    UP(90f, p -> GdxGameUtils.incrementedY(p)),
    DOWN(-90f, p -> GdxGameUtils.decrementedY(p)),
    LEFT(-180f, p -> GdxGameUtils.decrementedX(p)),
    RIGHT(0f, p -> GdxGameUtils.incrementedX(p));
    
    private final float rotation;
    private final Function<GridPoint2, GridPoint2> transformer;
    
    Direction(float rotation, Function<GridPoint2, GridPoint2> transformer) {
        this.rotation = rotation;
        this.transformer = transformer;
    }
    
    public GridPoint2 apply(GridPoint2 point) {
        return transformer.apply(point);
    }
    
    public float getRotation() {
        return rotation;
    }
}


public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private Texture blueTankTexture;
    private TextureRegion playerGraphics;
    private Rectangle playerRectangle;
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private GridPoint2 playerCoordinates;
    // which tile the player want to go next
    private GridPoint2 playerDestinationCoordinates;
    private float playerMovementProgress = 1f;
    private float playerRotation;

    private Texture greenTreeTexture;
    private TextureRegion treeObstacleGraphics;
    private GridPoint2 treeObstacleCoordinates = new GridPoint2();
    private Rectangle treeObstacleRectangle = new Rectangle();

    @Override
    public void create() {
       batch = new SpriteBatch();
    level = new TmxMapLoader().load("level.tmx");
    levelRenderer = createSingleLayerMapRenderer(level, batch);
    TiledMapTileLayer groundLayer = getSingleLayer(level);
    
    // Создание объектов через абстракции
    player = new Tank(new Texture("images/tank_blue.png"), groundLayer, new GridPoint2(1, 1));
    tree = new Tree(new Texture("images/greenTree.png"), groundLayer, new GridPoint2(1, 3));
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

         handleInput();
    player.update(deltaTime, MOVEMENT_SPEED);
    
    levelRenderer.render();
    batch.begin();
    player.render(batch);
    tree.render(batch);
    batch.end();
    }

    private void handleInput() {
    for (Direction dir : Direction.values()) {
        if (Gdx.input.isKeyPressed(dir.getKey())) {
            player.move(dir, tree.getCoordinates());
            break;
        }
    }
}

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        greenTreeTexture.dispose();
        blueTankTexture.dispose();
        level.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
