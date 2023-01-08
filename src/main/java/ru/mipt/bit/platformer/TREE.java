package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class TREE {
    private static Texture treeTexture;
    private static TextureRegion treeGraphics;
    private GridPoint2 treeCoordinates;
    private static Rectangle treeRectangle = new Rectangle();

    public TREE(GridPoint2 StartCoordinates){

        this.treeCoordinates = StartCoordinates;
        System.out.println(StartCoordinates);
    }
    public void CreateObstacleGraphics(String PathTexture){
        this.treeTexture = new Texture(PathTexture);
        this.treeGraphics = new TextureRegion(this.treeTexture);
        this.treeRectangle = createBoundingRectangle(treeGraphics);
    }
    public GridPoint2 ObstacleCoordinates() {
        return treeCoordinates;
    }

    public static Rectangle ObstacleRectangle() {
        return treeRectangle;
    }

    public void Render(Batch batch){
        drawTextureRegionUnscaled(batch, treeGraphics, treeRectangle, 0f);
    }
    public void dispose() {
        treeTexture.dispose();
    }
}