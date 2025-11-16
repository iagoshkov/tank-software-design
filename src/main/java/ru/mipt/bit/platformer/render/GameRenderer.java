package ru.mipt.bit.platformer.render;

/* 
 * Наблюдатель
*/
import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.observer.GameLevelObserver;
import java.util.ArrayList;
import java.util.List;

public class GameRenderer implements GameLevelObserver {
    private final Batch batch;
    private final List<GameObject> renderableObjects = new ArrayList<>();
    
    public GameRenderer(Batch batch) {
        this.batch = batch;
    }
    
    @Override
    public void onObjectAdded(GameObject object) {
        renderableObjects.add(object);
    }
    
    @Override
    public void onObjectRemoved(GameObject object) {
        renderableObjects.remove(object);
    }
    
    @Override
    public void onLevelInitialized(List<GameObject> objects) {
        renderableObjects.clear();
        renderableObjects.addAll(objects);
    }
    
    public void render() {
        for (GameObject object : renderableObjects) {
            if (object instanceof Renderable) {
                ((Renderable) object).render(batch);
            }
        }
    }
    
    public void dispose() {
        for (GameObject object : renderableObjects) {
            if (object instanceof Renderable) {
                ((Renderable) object).dispose();
            }
        }
    }
}