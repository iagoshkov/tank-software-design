package ru.mipt.bit.platformer.model;

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

import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank extends GameObject implements Renderable{
    private TextureRegion graphics;
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

    public void update(float deltaTime) {

    }
    
    public void updateMovement(float deltaTime, float speed) {
        movementProgress = continueProgress(movementProgress, deltaTime, speed);
        tileMovement.moveRectangleBetweenTileCenters(rectangle, coordinates, destinationCoordinates, movementProgress);
        
        if (movementProgress >= 1f) {
            coordinates.set(destinationCoordinates);
        }
    }
    
    public boolean isMoving() {
        return movementProgress < 1f;
    }
    
    public float getMovementProgress() {
        return movementProgress;
    }
}