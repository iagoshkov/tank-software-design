package ru.mipt.bit.platformer.Render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.GdxGameUtils;

public class Visualisation {
    public Texture texture;
    public TextureRegion textureRegion;
    public Rectangle rectangle;
    public Visualisation(String texturePath){
        this.texture = new Texture(texturePath);
        this.textureRegion = new TextureRegion(this.texture);
        this.rectangle = GdxGameUtils.createBoundingRectangle(this.textureRegion);
    }
    public void draw(Batch batch, float rotation){
        GdxGameUtils.drawTextureRegionUnscaled(batch, this.textureRegion, this.rectangle, rotation);
    }
    public void draw(Batch batch){
        GdxGameUtils.drawTextureRegionUnscaled(batch, this.textureRegion, this.rectangle, 0f);
    }
}
