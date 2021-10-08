package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.objects.Player;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class LevelLayer {
    public TiledMap level;
    public MapRenderer levelRenderer;
    public TileMovement tileMovement;
    public TiledMapTileLayer groundLayer;

    LevelLayer(TiledMap load, Batch batch){
        level = load;
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
    }

    public void updatePlayerPlacement(Player player) {
        tileMovement.moveRectangleBetweenTileCenters(player.getTexture().getRectangle(),
                                                     player.getCoordinates(),
                                                     player.getDestinationCoordinates(),
                                                     player.getMovementProgress());
    }

    public void placeObstacles(Tree tree) {
        moveRectangleAtTileCenter(groundLayer, tree.getRectangle(), tree.getCoordinates());
    }

    public void render(){
        levelRenderer.render();
    }

    public void dispose(){
        level.dispose();
    }

}
