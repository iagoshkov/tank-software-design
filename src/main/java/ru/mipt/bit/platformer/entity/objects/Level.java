package ru.mipt.bit.platformer.entity.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entity.objects.base.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private final List<GameObject> gameObjects = new ArrayList<>();;
    private final Integer height;
    private final Integer width;

    public Level(Integer height, Integer width) {
        this.height = height;
        this.width = width;
    }

    public void addGameObject(GameObject object) {
        gameObjects.add(object);
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public boolean isFreePoint(GridPoint2 point) {
        for (GameObject object : gameObjects) {
            if (object.isBusyCoordinate(point)) {
                return false;
            }
        }
        return true;
    }

    public boolean intoMapBorder(GridPoint2 point) {
        return (0 <= point.x && point.x < width) && (0 <= point.y && point.y < height);
    }

    public void updateState(float deltaTime) {
        for (GameObject object : gameObjects) {
            object.updateState(deltaTime);
        }
    }
}
