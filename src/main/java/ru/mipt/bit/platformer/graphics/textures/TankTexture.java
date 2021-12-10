package ru.mipt.bit.platformer.graphics.textures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.graphics.textures.HealthTexture;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class TankTexture {
    private final Texture blueTank;
    private final TextureRegion graphics;
    private final Rectangle rectangle;
    private final HealthTexture health;

    private boolean healthOn = false;

    public TankTexture(Texture tankTexture, Texture healthTexture){
        blueTank = tankTexture;
        this.graphics = new TextureRegion(blueTank);
        this.rectangle = createBoundingRectangle(this.graphics);
        this.health = new HealthTexture(healthTexture);
    }

    public TextureRegion getGraphics() {
        return graphics;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Texture getBlueTank() {
        return blueTank;
    }

    public Rectangle getHealthRectangle() {
        return health.getHealthRectangle();
    }

    public TextureRegion getHealthGraphics() {
        return health.getHealthGraphics();
    }

    public void changeHealthBar() {
        healthOn = !healthOn;
    }

    public boolean isHealthOn() {
        return healthOn;
    }
}
