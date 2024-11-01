package ru.mipt.bit.platformer.objects;

public interface Destroyable {
    void setHealth(int health);
    int getHealth();
    void destroy();
}
