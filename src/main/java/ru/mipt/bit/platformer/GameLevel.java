// ru/mipt/bit/platformer/GameLevel.java
package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.drawers.Renderable;
import ru.mipt.bit.platformer.objects.GameObject;

import java.util.ArrayList;
import java.util.List;

public class GameLevel implements LevelListener {
    private final Level logicLevel;
    private final List<Renderable> renderables;

    public GameLevel(Level logicLevel) {
        this.logicLevel = logicLevel;
        this.renderables = new ArrayList<>();
        this.logicLevel.addListener(this);
    }

    @Override
    public void onObjectAdded(GameObject object) {
        if (object instanceof Renderable) {
            renderables.add((Renderable) object);
        }
    }

    @Override
    public void onObjectRemoved(GameObject object) {
        if (object instanceof Renderable) {
            renderables.remove(object);
            object.dispose();
        }
    }

    @Override
    public void onLevelInitialized() {
        // Инициализация завершена
        renderables.clear();
        for (GameObject obj : logicLevel.getGameObjects()) {
            if (obj instanceof Renderable) {
                renderables.add((Renderable) obj);
            }
        }
    }

    public void render(Batch batch) {
        logicLevel.render(batch);
        for (Renderable renderable : renderables) {
            renderable.draw(batch);
        }
    }

    public void update(float deltaTime) {
        // Обновляем все обновляемые объекты
        for (GameObject obj : logicLevel.getGameObjects()) {
            if (obj instanceof ru.mipt.bit.platformer.drawers.Updatable) {
                ((ru.mipt.bit.platformer.drawers.Updatable) obj).update(deltaTime);
            }
        }
    }

    public void dispose() {
        for (Renderable renderable : renderables) {
            renderable.dispose();
        }
        logicLevel.dispose();
    }

    public Level getLogicLevel() {
        return logicLevel;
    }
}