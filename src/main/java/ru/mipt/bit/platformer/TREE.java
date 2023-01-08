package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class TREE {
    private Texture treeTexture;
    private TextureRegion treeGraphics;
    private GridPoint2 treeCoordinates;
    private Rectangle treeRectangle = new Rectangle();

    public TREE(GridPoint2 StartCoordinates){
        this.treeCoordinates = StartCoordinates;
        //System.out.println("do tree");
        System.out.println(StartCoordinates);
    }
    public void CreateTreeGraphics(String PathTexture){
        this.treeTexture = new Texture(PathTexture);
        this.treeGraphics = new TextureRegion(this.treeTexture);
        this.treeRectangle = createBoundingRectangle(this.treeGraphics);
    }
    public GridPoint2 TreeCoordinates() {
        return this.treeCoordinates;
    }

    public Rectangle TreeRectangle() {
        return this.treeRectangle;
    }

    public void Render(Batch batch){
        //System.out.println("Render tree");
        //System.out.println(treeCoordinates);
        drawTextureRegionUnscaled(batch, this.treeGraphics, this.treeRectangle, 0f);
    }
    public void dispose() {
        treeTexture.dispose();
    }
}