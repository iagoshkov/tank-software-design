package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.Tank;

/** Infrastructure: decorator that draws a health bar over a tank. */
public class HealthBarDecorator implements Renderable {

    private final Renderable renderable;
    private final Tank tank;
    private final HealthBarToggle toggle;
    private final Texture texture;

    public HealthBarDecorator(Renderable renderable, Tank tank, HealthBarToggle toggle, Texture texture) {
        this.renderable = renderable;
        this.tank = tank;
        this.toggle = toggle;
        this.texture = texture;
    }

    @Override
    public void render(Batch batch) {
        renderable.render(batch);
        if (!toggle.isVisible()) {
            return;
        }

        Rectangle rectangle = getRectangle();
        HealthBarLayout bar = new HealthBarLayout(rectangle.x, rectangle.y, rectangle.width,
                rectangle.height, tank.getHealth(), tank.getMaxHealth());

        float color = batch.getPackedColor();
        try {
            batch.setColor(Color.RED);
            batch.draw(texture, bar.x, bar.y, bar.width, bar.height);
            batch.setColor(Color.GREEN);
            batch.draw(texture, bar.x, bar.y, bar.filledWidth, bar.height);
        } finally {
            batch.setPackedColor(color);
        }
    }

    @Override
    public Rectangle getRectangle() {
        return renderable.getRectangle();
    }
}
