package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.objects.GameObject;
import ru.mipt.bit.platformer.objects.Player;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class Level {
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final TileMovement tileMovement;
    private final TiledMapTileLayer groundLayer;
    private final List<GameObject> gameObjects;
    private final Map<GridPoint2, Tank> occupiedPositions;
    private final List<Tree> trees;
    private final int levelWidth;
    private final int levelHeight;

    public Level() {
        map = new TmxMapLoader().load("src/main/resources/level.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        groundLayer = (TiledMapTileLayer) map.getLayers().get(0);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        gameObjects = new ArrayList<>();
        occupiedPositions = new HashMap<>();
        trees = new ArrayList<>();
        
        levelWidth = groundLayer.getWidth();
        levelHeight = groundLayer.getHeight();
    }

    public void placeObject(GameObject object) {
        gameObjects.add(object);
        moveRectangleAtTileCenter(groundLayer, object.getBounds(), object.getCoordinates());
        
        if (object instanceof Tree) {
            trees.add((Tree) object);
        }
    }

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    
    public boolean isPositionValid(GridPoint2 position) {
        return position.x >= 0 && position.x < levelWidth && 
               position.y >= 0 && position.y < levelHeight;
    }

    public boolean isPositionOccupied(GridPoint2 position) {
        return occupiedPositions.containsKey(position);
    }

    public boolean isPositionBlockedByTree(GridPoint2 position) {
        for (Tree tree : trees) {
            if (tree.getCoordinates().equals(position)) {
                return true;
            }
        }
        return false;
    }

    public void reservePosition(GridPoint2 position, Tank tank) {
        occupiedPositions.put(new GridPoint2(position), tank);
    }

    public void freePosition(GridPoint2 position) {
        occupiedPositions.remove(position);
    }

    public void render(Batch batch) {
        mapRenderer.setView(batch.getProjectionMatrix(), 0, 0, 1280, 1024);
        mapRenderer.render();
    }

    public void dispose() {
        map.dispose();
        mapRenderer.dispose();
    }

    public TileMovement getTileMovement() {
        return tileMovement;
    }

    public TiledMapTileLayer getGroundLayer() {
        return groundLayer;
    }

    public int getLevelWidth() {
        return levelWidth;
    }

    public int getLevelHeight() {
        return levelHeight;
    }

    public boolean isPositionOccupiedByPlayer(GridPoint2 position, Tank excludingTank) {
    for (GameObject obj : gameObjects) {
        if (obj instanceof Player) {
            Player player = (Player) obj;
            if (player.getCoordinates().equals(position) || 
                (player.isMoving() && player.getDestinationCoordinates().equals(position))) {
                return true;
            }
        } else if (obj instanceof Tank) {
            Tank tank = (Tank) obj;
            // Игрок управляемый танк
            if (tank.isPlayerControlled() && tank != excludingTank) {
                if (tank.getCoordinates().equals(position) || 
                    (tank.isMoving() && tank.getDestinationCoordinates().equals(position))) {
                    return true;
                }
            }
        }
    }
    return false;
}
}