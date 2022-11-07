package ru.mipt.bit.platformer.util.observer;

import ru.mipt.bit.platformer.util.objects.BaseObject;

public interface Observable {
    void addObserver(Observer o);
    void removeObserver(Observer o);
    void giveSignal(BulletAction bulletAction, BaseObject object);
}
