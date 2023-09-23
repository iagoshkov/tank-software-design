package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.Entities.MapObject;
import ru.mipt.bit.platformer.Entities.Tank;
import ru.mipt.bit.platformer.Entities.Tree;
import ru.mipt.bit.platformer.graphics.Graphics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class GraphicsController implements Disposable {
    private final Map<Class<? extends MapObject>, String> objectTexturesPathMap = new HashMap<>();
    private final Map<MapObject, Graphics> objectToGraphicsMap = new HashMap<>();


    GraphicsController() {
        objectTexturesPathMap.put(Tank.class, "images/tank_blue.png");
        objectTexturesPathMap.put(Tree.class, "images/greenTree.png");
    }

    public void addGraphicsOf(MapObject object) {
        objectToGraphicsMap.put(object, new Graphics(objectTexturesPathMap.get(object.getClass())));
    }

    public void addGraphicsOf(List<? extends MapObject> objects) {
        for (MapObject object: objects) {
            objectToGraphicsMap.put(object, new Graphics(objectTexturesPathMap.get(object.getClass())));
        }
    }

    public void drawObject(Batch batch, MapObject object) {
        drawTextureRegionUnscaled(batch, objectToGraphicsMap.get(object).getRegion(), objectToGraphicsMap.get(object).getRectangle(), object.getRotation());
    }

    public Graphics getGraphics(MapObject object) {
        return objectToGraphicsMap.get(object);
    }

    @Override
    public void dispose() {
        objectToGraphicsMap.values().forEach(Graphics::dispose);
    }

}
