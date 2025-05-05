package ru.mipt.bit.platformer.entity.draw.base;

import ru.mipt.bit.platformer.entity.objects.base.GameObject;

public interface GraphicFactory {
    GameObjectGraphic create(GameObject object);
}
