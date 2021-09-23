package ru.mipt.bit.platformer.Player;

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

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tank {
    private static final float MOVEMENT_SPEED = 0.4f;
    private TextureRegion playerGraphics;
    private Rectangle playerRectangle;
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private GridPoint2 playerCoordinates = new GridPoint2(0, 1);
    // which tile the player want to go next
    private GridPoint2 playerDestinationCoordinates = new GridPoint2(1, 1);
    private float playerMovementProgress = 1f;
    private float playerRotation;

    public Tank(Texture blueTankTexture){
        playerGraphics = new TextureRegion(blueTankTexture);
        playerRectangle = createBoundingRectangle(playerGraphics);
    }

    public TextureRegion getPlayerGraphics() {
        return playerGraphics;
    }

    public void setPlayerGraphics(TextureRegion playerGraphics) {
        this.playerGraphics = playerGraphics;
    }

    public Rectangle getPlayerRectangle() {
        return playerRectangle;
    }

    public void setPlayerRectangle(Rectangle playerRectangle) {
        this.playerRectangle = playerRectangle;
    }

    public GridPoint2 getPlayerDestinationCoordinates() {
        return playerDestinationCoordinates;
    }

    public void setPlayerDestinationCoordinates(GridPoint2 playerDestinationCoordinates) {
        this.playerDestinationCoordinates.x = playerDestinationCoordinates.x;
        this.playerDestinationCoordinates.y = playerDestinationCoordinates.y;
    }

    public GridPoint2 getPlayerCoordinates() {
        return playerCoordinates;
    }

    public void setPlayerCoordinates(GridPoint2 playerCoordinates) {
        this.playerCoordinates = playerCoordinates;
    }

    public float getPlayerMovementProgress() {
        return playerMovementProgress;
    }

    public void setPlayerMovementProgress(float playerMovementProgress) {
        this.playerMovementProgress = playerMovementProgress;
    }

    public float getPlayerRotation() {
        return playerRotation;
    }

    public void setPlayerRotation(float playerRotation) {
        this.playerRotation = playerRotation;
    }

    public boolean CheckMovementProgres(float playerMovementProgres1){
        return isEqual(playerMovementProgress, playerMovementProgres1);
    }
    public float GetMovementSpeed(){
        return MOVEMENT_SPEED;
    }

}
