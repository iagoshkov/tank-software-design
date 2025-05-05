package ru.mipt.bit.platformer.entity.draw.drawers;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.graphics.g2d.Batch;

import ru.mipt.bit.platformer.entity.draw.base.GameObjectGraphic;
import ru.mipt.bit.platformer.entity.draw.base.GraphicFactory;
import ru.mipt.bit.platformer.entity.draw.base.LevelGraphic;
import ru.mipt.bit.platformer.entity.objects.*;
import ru.mipt.bit.platformer.entity.objects.base.GameObject;

import java.util.HashMap;
import java.util.Map;

public class LevelDrawer implements LevelGraphic {
    private final Batch batch;
    private TiledMap map;
    private MapRenderer renderer;
    private final Level level;
    private final Map<Class<? extends GameObject>, GraphicFactory> strategyGraphics = new HashMap<>();
    private final Map<GameObject, GameObjectGraphic> graphicObjects = new HashMap<>();

    public LevelDrawer(TiledMap map, MapRenderer renderer, Batch batch, Level level) {
        this.map = map;
        this.renderer = renderer;
        this.level = level;
        this.batch = batch;
    }

    public Batch getBatch() {
        return batch;
    }

    @Override
    public void addStrategyGraphics(Class<? extends GameObject> clazz, GraphicFactory gameObjectGraphic) {
        strategyGraphics.put(clazz, gameObjectGraphic);
    }

    @Override
    public void addGraphicObject(GameObject object) {
        GameObjectGraphic gameObjectGraphic = strategyGraphics.get(object.getClass()).create(object);
        graphicObjects.put(object, gameObjectGraphic);
    }

    public Map<GameObject, GameObjectGraphic> getGraphicObjects() {
        return graphicObjects;
    }

    public void renderObjects() {
        for (GameObjectGraphic drawer : graphicObjects.values()) {
            drawer.draw(getBatch());
        }
    }

    public void render() {
        renderer.render();
        batch.begin();
        renderObjects();
        batch.end();
    }

    public void dispose() {
        for (GameObjectGraphic drawer : graphicObjects.values()) {
            drawer.dispose();
        }

        map.dispose();
        batch.dispose();
    }
}
