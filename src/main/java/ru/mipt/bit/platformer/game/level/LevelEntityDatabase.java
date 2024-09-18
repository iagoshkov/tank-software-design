package ru.mipt.bit.platformer.game.level;

import java.util.ArrayList;
import java.util.List;

public class LevelEntityDatabase {
    /*
    Класс, хранящий методы создания готовых объектов и коллекцию всех созданных объектов (в данном случае танка и дерева).
     */

    // Созданные объекты передаются и удаляются в LevelRenderer при остановке приложения
    public static List<LevelEntity> createdObjects = new ArrayList<>();

    private static LevelEntity getLevelObject(String path) {
        LevelEntity object = new LevelEntity(path);
        createdObjects.add(object);
        return object;
    }

    public static LevelEntity getBlueTank() {
        return getLevelObject("images/tank_blue.png");
    }

    public static LevelEntity getGreenTree() {
        return getLevelObject("images/greenTree.png");
    }
}
