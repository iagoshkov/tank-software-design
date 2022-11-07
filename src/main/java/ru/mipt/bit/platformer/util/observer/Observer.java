package ru.mipt.bit.platformer.util.observer;

import ru.mipt.bit.platformer.util.objects.BaseObject;

public interface Observer {
    void update(BulletAction bulletAction, BaseObject gameObject, int id);
}
