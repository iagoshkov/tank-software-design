package ru.mipt.bit.platformer.game;

import java.util.ArrayList;
import java.util.List;

public class LevelObjectDatabase {
    /*
    Класс, хранящий методы создания готовых объектов (в данном случае танка и дерева).
     */

    // Созданные объекты передаются и удаляются в LevelRenderer при остановке приложения
    public static List<LevelObject> createdObjects = new ArrayList<>();

    private static LevelObject getLevelObject(String path) {
        LevelObject object = new LevelObject(path);
        createdObjects.add(object);
        return object;
    }

    public static LevelObject getBlueTank() {
        return getLevelObject("images/tank_blue.png");
    }

    public static LevelObject getGreenTree() {
        return getLevelObject("images/greenTree.png");
    }
}
