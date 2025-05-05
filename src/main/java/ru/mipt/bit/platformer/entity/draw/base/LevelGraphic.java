package ru.mipt.bit.platformer.entity.draw.base;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.entity.objects.base.GameObject;

import java.util.Map;

public interface LevelGraphic {
    void render();
    void dispose();
    Batch getBatch();
    Map<GameObject, GameObjectGraphic> getGraphicObjects();
    void addStrategyGraphics(Class<? extends GameObject> clazz, GraphicFactory graphicsFactory);
    void addGraphicObject(GameObject object);
}
