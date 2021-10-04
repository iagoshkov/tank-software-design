package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.g2d.Batch;

import com.badlogic.gdx.maps.MapRenderer;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.GraphicsObjects;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.player.GraphicsPlayer;
import ru.mipt.bit.platformer.player.move.Movement;
import ru.mipt.bit.platformer.player.Player;
import ru.mipt.bit.platformer.player.move.NewDestination;
import ru.mipt.bit.platformer.util.TileMovement;


public class Render {
    private static final float MOVEMENT_SPEED = 0.4f;

    private final Batch batch;
    private final MapRenderer levelRenderer;
    private final TileMovement tileMovement;
    private final TiledMapTileLayer groundLayer;
    private final Player player;
    private final Tree tree;
    private GraphicsPlayer graphicsPlayer;
    private GraphicsObjects graphicsTree;


    public Render(Batch batch, MapRenderer levelRenderer, TileMovement tileMovement, TiledMapTileLayer groundLayer, Player player, Tree tree) {
        this.batch = batch;
        this.levelRenderer = levelRenderer;
        this.tileMovement = tileMovement;
        this.groundLayer = groundLayer;
        this.player = player;
        this.tree = tree;
    }

    public void doRender(){
        graphicsPlayer = new GraphicsPlayer(player,batch,tileMovement);
        graphicsTree = new GraphicsObjects(batch,tree);
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);


        // get time passed since the last render
        float deltaTime = player.getDeltaTime();


        if (isEqual(player.getMovementProgress(), 1f)) {
                Movement.movementKey(Gdx.input);
                if (!tree.getObstacleCoordinates().equals(NewDestination.newCoordinates())) {
                    NewDestination.setDestinationCoordinates(NewDestination.newCoordinates());
                    player.setMovementProgress(0f);
                    Movement.setCurrMovementVector(new GridPoint2(0,0));
                }
                player.setRotation(Movement.getCurrRotation());
            }


        // calculate interpolated player screen coordinates
        GraphicsPlayer.CalcInterpPlayerCoordinate();

        player.setMovementProgress(continueProgress(player.getMovementProgress(), deltaTime, MOVEMENT_SPEED));
        if (isEqual(player.getMovementProgress(), 1f)) {
            // record that the player has reached his/her destination
            player.setCoordinates(NewDestination.getDestinationCoordinates());
        }

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        graphicsPlayer.DrawPlayer();

        // render tree obstacle
        graphicsTree.DrawTree();

        // submit all drawing requests
        batch.end();
    }

}
