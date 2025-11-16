package ru.mipt.bit.platformer.observer;

import ru.mipt.bit.platformer.model.GameObject;
import java.util.ArrayList;
import java.util.List;

public class GameLevelObservable {
    private final List<GameLevelObserver> observers = new ArrayList<>();
    private final List<GameObject> gameObjects = new ArrayList<>();
    
    public void addObserver(GameLevelObserver observer) {
        observers.add(observer);
    }
    
    public void removeObserver(GameLevelObserver observer) {
        observers.remove(observer);
    }
    
    public void addGameObject(GameObject object) {
        gameObjects.add(object);
        notifyObjectAdded(object);
    }
    
    public void removeGameObject(GameObject object) {
        gameObjects.remove(object);
        notifyObjectRemoved(object);
    }
    
    public void notifyLevelInitialized() {
        for (GameLevelObserver observer : observers) {
            observer.onLevelInitialized(new ArrayList<>(gameObjects));
        }
    }
    
    private void notifyObjectAdded(GameObject object) {
        for (GameLevelObserver observer : observers) {
            observer.onObjectAdded(object);
        }
    }
    
    private void notifyObjectRemoved(GameObject object) {
        for (GameLevelObserver observer : observers) {
            observer.onObjectRemoved(object);
        }
    }
    
    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }
}