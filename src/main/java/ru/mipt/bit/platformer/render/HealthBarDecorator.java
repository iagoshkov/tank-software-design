package ru.mipt.bit.platformer.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class HealthBarDecorator {
    private final ShapeRenderer shapeRenderer;
    
    public HealthBarDecorator() {
        this.shapeRenderer = new ShapeRenderer();
    }
    
    public void renderHealthBar(Batch batch, Rectangle objectBounds, float healthPercentage) {
        if (healthPercentage >= 1.0f) return; // Не показывать если полное здоровье
        
        batch.end(); 
        
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        
        // Фон полоски здоровья - красный
        float barWidth = objectBounds.width * 0.8f;
        float barHeight = 5f;
        float barX = objectBounds.x + (objectBounds.width - barWidth) / 2;
        float barY = objectBounds.y + objectBounds.height + 5f;
        
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(barX, barY, barWidth, barHeight);
        
        // Здоровье - зеленый
        float healthWidth = barWidth * healthPercentage;
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(barX, barY, healthWidth, barHeight);
        
        shapeRenderer.end();
        
        batch.begin(); 
    }
    
    public void dispose() {
        shapeRenderer.dispose();
    }
}