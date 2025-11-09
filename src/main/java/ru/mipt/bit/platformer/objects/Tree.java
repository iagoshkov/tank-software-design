package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Level;
import ru.mipt.bit.platformer.configs.TreeConfig;

public class Tree extends GameObject {
    private final TextureRegion graphics;
    private Level level;

    public Tree(TreeConfig config, Level level) {
        super(config.getInitialPosition(), 1); // Деревья имеют 1 HP (неуязвимы)
        this.level = level;
        this.graphics = new TextureRegion(new Texture(config.getTexturePath()));
        this.bounds = ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle(graphics);
        
        level.placeObject(this);
    }


    @Override
    public void draw(Batch batch) {
        ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled(batch, graphics, bounds, rotation);
    }

    @Override
    public void dispose() {
        graphics.getTexture().dispose();
    }
}