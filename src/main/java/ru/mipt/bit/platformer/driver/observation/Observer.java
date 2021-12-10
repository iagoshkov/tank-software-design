package ru.mipt.bit.platformer.driver.observation;

import ru.mipt.bit.platformer.objects.gameObjects.GameObject;

public interface Observer {
    void update(Event event, GameObject gameObject, int id);
}
