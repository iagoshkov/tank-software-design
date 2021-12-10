package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.graphics.textures.BulletTexture;
import ru.mipt.bit.platformer.graphics.textures.TankTexture;
import ru.mipt.bit.platformer.graphics.textures.TreeTexture;
import ru.mipt.bit.platformer.objects.gameObjects.Bullet;
import ru.mipt.bit.platformer.objects.gameObjects.Tank;
import ru.mipt.bit.platformer.objects.gameObjects.Tree;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.List;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class LevelLayer {
    public TiledMap level;
    public MapRenderer levelRenderer;
    public TileMovement tileMovementTank;
    public TileMovement tileMovementBullet;
    public TiledMapTileLayer groundLayer;

    public LevelLayer(TiledMap load, Batch batch){
        level = load;
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        groundLayer = getSingleLayer(level);
        tileMovementTank = new TileMovement(groundLayer, Interpolation.smooth);
        tileMovementBullet = new TileMovement(groundLayer, Interpolation.linear);
    }

    public void updateTanksPlacement(Tank tank, TankTexture tankTexture) {
        tileMovementTank.moveRectangleBetweenTileCenters(tankTexture.getRectangle(),
                                                     tank.getCoordinates(),
                                                     tank.getDestinationCoordinates(),
                                                     tank.getMovementProgress());

        tileMovementTank.moveRectangleBetweenTileCenters(tankTexture.getHealthRectangle(),
                                                         tank.getCoordinates(),
                                                         tank.getDestinationCoordinates(),
                                                         tank.getMovementProgress());
    }

    public void updateBulletsPlacement(Bullet bullet, BulletTexture bulletTexture) {
        tileMovementBullet.moveRectangleBetweenTileCenters(bulletTexture.getRectangle(),
                                                           bullet.getCoordinates(),
                                                           bullet.getDestinationCoordinates(),
                                                           bullet.getMovementProgress());
    }

    public void placeObstacles(List<Tree> trees, List<TreeTexture> treeTextures) {
        for (int i = 0; i < trees.size(); i++) {
            moveRectangleAtTileCenter(groundLayer, treeTextures.get(i).getRectangle(), trees.get(i).getCoordinates());
        }

    }

    public void render(){
        levelRenderer.render();
    }

    public void dispose(){
        level.dispose();
    }

}
