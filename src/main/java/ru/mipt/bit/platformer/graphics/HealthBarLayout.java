package ru.mipt.bit.platformer.graphics;

/** Infrastructure: calculates bar dimensions without drawing. */
public class HealthBarLayout {

    public final float x;
    public final float y;
    public final float width;
    public final float height = 8f;
    public final float filledWidth;

    public HealthBarLayout(float x, float y, float width, float height, int health, int maxHealth) {
        this.x = x;
        this.y = y + height + 6f;
        this.width = width;
        float fraction = maxHealth > 0 ? (float) health / maxHealth : 0f;
        filledWidth = width * Math.max(0f, Math.min(1f, fraction));
    }
}
