package ru.mipt.bit.platformer.entity.draw.decorators;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.entity.draw.base.LevelGraphic;
import ru.mipt.bit.platformer.entity.draw.decorators.base.LevelDrawerDecorator;
import ru.mipt.bit.platformer.entity.draw.decorators.base.Toggle;
import ru.mipt.bit.platformer.entity.objects.base.GameObject;
import ru.mipt.bit.platformer.entity.objects.base.Livable;
import ru.mipt.bit.platformer.util.GdxGameUtils;

public class HealthBarDrawerDecorator extends LevelDrawerDecorator implements Toggle {
    private final Batch batch;
    private boolean toggleOn;

    public HealthBarDrawerDecorator(LevelGraphic levelGraphics) {
        this.levelGraphics = levelGraphics;
        this.batch = getBatch();
        this.toggleOn = false;
    }

    private static TextureRegion createBar(float health, Color color) {
        Pixmap pixmap = new Pixmap((int) (90 * health / 100), 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, (int) (90 * health / 100), 20);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegion(texture);
    }

    @Override
    public void render() {
        levelGraphics.render();

        if (!toggleOn){
            return;
        }
        renderHealthBar();
    }

    public boolean isLivableObject(GameObject object) {
        return object instanceof Livable;
    }

    private void renderHealthBar() {
        batch.begin();
        levelGraphics.getGraphicObjects().forEach((key, value) -> {
            if (isLivableObject(key)) {
                drawHealthBar(getBatch(), value.getRectangle(), ((Livable) key).getHealth());
                value.draw(getBatch());
            }
        });
        batch.end();
    }

    private void drawHealthBar(Batch batch, Rectangle rectangle, float health) {
        TextureRegion healthBgBar = createBar(100, Color.RED);
        TextureRegion healthLeftBar = createBar(health, Color.GREEN);
        Rectangle hpRectangle = new Rectangle(rectangle);
        hpRectangle.y += 90;
        GdxGameUtils.drawTextureRegionUnscaled(batch, healthBgBar, hpRectangle, 0);
        GdxGameUtils.drawTextureRegionUnscaled(batch, healthLeftBar, hpRectangle, 0);
    }

    @Override
    public void switchToggle() {
        this.toggleOn = !toggleOn;
    }
}
