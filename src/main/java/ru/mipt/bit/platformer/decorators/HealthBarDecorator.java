package ru.mipt.bit.platformer.decorators;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.drawers.Renderable;
import ru.mipt.bit.platformer.objects.GameObject;

public class HealthBarDecorator implements Renderable {
    private final Renderable wrapped;
    private final GameObject gameObject;
    private final ShapeRenderer shapeRenderer;

    public HealthBarDecorator(Renderable wrapped, GameObject gameObject) {
        this.wrapped = wrapped;
        this.gameObject = gameObject;
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch) {
        // Сначала рисуем основной объект
        wrapped.draw(batch);
        
        // Затем рисуем полоску здоровья если нужно
        if (gameObject.shouldShowHealthBar()) {
            drawHealthBar(batch);
        }
    }

    private void drawHealthBar(Batch batch) {
        batch.end(); // Завершаем batch для использования ShapeRenderer
        
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        
        Rectangle bounds = gameObject.getBounds();
        float healthPercent = gameObject.getHealthPercentage();
        
        // Фон полоски здоровья (красный)
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(bounds.x, bounds.y + bounds.height + 2, bounds.width, 3);
        
        // Заполненная часть (зеленый)
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(bounds.x, bounds.y + bounds.height + 2, bounds.width * healthPercent, 3);
        
        shapeRenderer.end();
        
        batch.begin(); // Возобновляем batch
    }

    @Override
    public void dispose() {
        wrapped.dispose();
        shapeRenderer.dispose();
    }
}