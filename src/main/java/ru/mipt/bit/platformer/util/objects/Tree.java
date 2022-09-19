package ru.mipt.bit.platformer.util.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;


import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class Tree{
    public Texture greenTreeTexture;
    public TextureRegion treeObstacleGraphics;
    public GridPoint2 treeObstacleCoordinates = new GridPoint2();
    public Rectangle treeObstacleRectangle = new Rectangle();

    public void createTreeObject(TiledMapTileLayer groundLayer){
        greenTreeTexture = new Texture("images/greenTree.png");
        treeObstacleGraphics = new TextureRegion(greenTreeTexture);
        treeObstacleCoordinates = new GridPoint2(1, 3);
        treeObstacleRectangle = createBoundingRectangle(treeObstacleGraphics);
        moveRectangleAtTileCenter(groundLayer, treeObstacleRectangle, treeObstacleCoordinates);
    }
}
