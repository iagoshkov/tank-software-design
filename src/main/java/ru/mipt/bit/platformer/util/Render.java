package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.Gdx;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.Player.Tank;
import ru.mipt.bit.platformer.Player.Movement.UpMove;
import ru.mipt.bit.platformer.Player.Movement.LeftMove;
import ru.mipt.bit.platformer.Player.Movement.DownMove;
import ru.mipt.bit.platformer.Player.Movement.RightMove;

public class Render {
    private final Batch batch;
    private final MapRenderer levelRenderer;
    private final TileMovement tileMovement;
    private final TiledMapTileLayer groundLayer;
    private final Tank tank;

    private float deltaTime;

    public Render(Batch batch, MapRenderer levelRenderer, TileMovement tileMovement, TiledMapTileLayer groundLayer, Tank tank) {
        this.batch = batch;
        this.levelRenderer = levelRenderer;
        this.tileMovement = tileMovement;
        this.groundLayer = groundLayer;
        this.tank = tank;
    }

    public void calculateCoordinate(){
        tileMovement.moveRectangleBetweenTileCenters(tank.getPlayerRectangle(),
                tank.getPlayerCoordinates(), tank.getPlayerDestinationCoordinates(),
                tank.getPlayerMovementProgress());
        tank.setPlayerMovementProgress(continueProgress(tank.getPlayerMovementProgress(),
                deltaTime, tank.GetMovementSpeed()));
        if (isEqual(tank.getPlayerMovementProgress(), 1f)) {
            tank.setPlayerCoordinates(tank.getPlayerDestinationCoordinates());
        }
    }
    public void clear(){
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    public void computeDelta(){
        deltaTime = Gdx.graphics.getDeltaTime();
    }
    public void action(){
        UpMove.makeMove();
        LeftMove.makeMove();
        DownMove.makeMove();
        RightMove.makeMove();
    }
    public void setDeltaTime(float deltaTime) {
        this.deltaTime = deltaTime;
    }

    public TileMovement getTileMovement() {
        return tileMovement;
    }

    public MapRenderer getLevelRenderer() {
        return levelRenderer;
    }

    public Batch getBatch() {
        return batch;
    }

    public TiledMapTileLayer getGroundLayer() {
        return groundLayer;
    }
}