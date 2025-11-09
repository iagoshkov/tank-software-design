package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import ru.mipt.bit.platformer.drawers.Renderable;

public abstract class GameObject implements Renderable {
    protected GridPoint2 coordinates;
    protected Rectangle bounds;
    protected float rotation;
    protected int health;
    protected int maxHealth;
    protected boolean showHealthBar;

    public GameObject(GridPoint2 coordinates, int maxHealth) {
        this.coordinates = new GridPoint2(coordinates);
        this.rotation = 0f;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.showHealthBar = false;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setHealth(int health) {
        this.health = Math.min(Math.max(0, health), maxHealth);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void setShowHealthBar(boolean show) {
        this.showHealthBar = show;
    }

    public boolean shouldShowHealthBar() {
        return showHealthBar && isAlive();
    }

    public float getHealthPercentage() {
        return (float) health / maxHealth;
    }
}