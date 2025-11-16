package ru.mipt.bit.platformer.observer;

import ru.mipt.bit.platformer.model.GameObject;

public interface GameLevelObserver {
    void onObjectAdded(GameObject object);
    void onObjectRemoved(GameObject object);
    void onLevelInitialized(java.util.List<GameObject> objects);
}