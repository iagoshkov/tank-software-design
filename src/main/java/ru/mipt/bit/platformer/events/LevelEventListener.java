package ru.mipt.bit.platformer.events;

import ru.mipt.bit.platformer.objects.GameObject;

public interface LevelEventListener {
    void onObjectAdded(GameObject object);
    void onObjectRemoved(GameObject object);
}