package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.g2d.Batch;

import com.badlogic.gdx.maps.MapRenderer;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.player.Player;
import ru.mipt.bit.platformer.util.TileMovement;


public class Render {
    private final Batch batch;
    private final MapRenderer levelRenderer;
    private final TileMovement tileMovement;
    private final TiledMapTileLayer groundLayer;
    private final Player player;
    private final Tree tree;


    private float deltaTime;

    public Render(Batch batch, MapRenderer levelRenderer, TileMovement tileMovement, TiledMapTileLayer groundLayer, Player player, Tree tree) {
        this.batch = batch;
        this.levelRenderer = levelRenderer;
        this.tileMovement = tileMovement;
        this.groundLayer = groundLayer;
        this.player = player;
        this.tree = tree;
    }

    public void doRender(){
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        deltaTime = Gdx.graphics.getDeltaTime();


            if (isEqual(player.movementProgress, 1f)) {
                player.movementKey(Gdx.input);
                if (!tree.getObstacleCoordinates().equals(player.newCoordinates())) {
                    player.destinationCoordinates = player.newCoordinates();
                    player.movementProgress = 0f;
                    player.currMovementVector.x = 0;
                    player.currMovementVector.y = 0;
                }
                player.rotation = player.currRotation;
            }


        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(player.rectangle, player.coordinates, player.destinationCoordinates, player.movementProgress);

        player.movementProgress = continueProgress(player.movementProgress, deltaTime, player.MOVEMENT_SPEED);
        if (isEqual(player.movementProgress, 1f)) {
            // record that the player has reached his/her destination
            player.coordinates.set(player.destinationCoordinates);
        }

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        drawTextureRegionUnscaled(batch, player.graphics, player.rectangle, player.rotation);

        // render tree obstacle
        drawTextureRegionUnscaled(batch, tree.getObstacleGraphics(), tree.getObstacleRectangle(), 0f);

        // submit all drawing requests
        batch.end();
    }
}
