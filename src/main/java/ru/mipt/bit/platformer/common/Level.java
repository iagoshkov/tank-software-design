package ru.mipt.bit.platformer.common;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.controllers.InputController;
import ru.mipt.bit.platformer.entities.MapObject;
import ru.mipt.bit.platformer.entities.Tank;
import ru.mipt.bit.platformer.entities.Tree;
import ru.mipt.bit.platformer.instructions.Instruction;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Level {
    private final List<ObjectAddHandler> handlerList = new ArrayList<>();
    private final InputController inputController;
    private final List<MapObject> objects = new ArrayList<>();

    private MapObject player;

    public Level(MapObject player, InputController inputController, ObjectAddHandler... handlers) {
        this.handlerList.addAll(List.of(handlers));
        this.inputController = inputController;
        this.player = player;
        add(player);
    }

    public Level(String fileName, InputController inputController, ObjectAddHandler... handlers) {
        this.handlerList.addAll(List.of(handlers));
        this.inputController = inputController;
        addObjectsFromFile(fileName);
    }

    public Level(int width, int height, InputController inputController, ObjectAddHandler... handlers) {
        this.handlerList.addAll(List.of(handlers));
        this.inputController = inputController;

        generateObjects(width, height);
    }

    private void generateObjects(int width, int height) {
        Set<GridPoint2> generated = new HashSet<>();

        GridPoint2 playerCoordinates = genRandomGridPoint2(width, height);
        generated.add(playerCoordinates);
        this.player = new Tank(playerCoordinates);
        this.add(player);

        genObstacles(width, height, generated);
    }

    private void genObstacles(int width, int height, Set<GridPoint2> generated) {
        int genCount = ThreadLocalRandom.current().nextInt(1, width * height - 1);
        for (int i = 0; i < genCount; i++) {
            GridPoint2 coordinates = genRandomGridPoint2(width, height);
            if (!generated.contains(coordinates)) {
                generated.add(coordinates);
                this.add(new Tree(coordinates));
            }
        }
    }

    private GridPoint2 genRandomGridPoint2(int width, int height) {
        int x = ThreadLocalRandom.current().nextInt(0, width - 1);
        int y = ThreadLocalRandom.current().nextInt(0, height - 1);
        return new GridPoint2(x, y);

    }

    private void addObjectsFromFile(String path) {
        Map<Character, Constructor<? extends MapObject>> charToConstructorMap = getCharToConstructorMap();
        setObjectsFromFile(path, charToConstructorMap);
    }

    private void setObjectsFromFile(String path, Map<Character, Constructor<? extends MapObject>> charToConstructorMap) {
        try {
            List<String> allLines = Files.readAllLines(Paths.get(path));

            for (int i = allLines.size() - 1; i >= 0; --i) {
                String line = allLines.get(i);
                for (int x = 0; x < line.length(); x++) {
                    setObjectByChar(charToConstructorMap, line.charAt(x),  x, allLines.size() - i - 1);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setObjectByChar(Map<Character, Constructor<? extends MapObject>> charToConstructorMap, char c, int x, int y) {
        try {
            Constructor<? extends MapObject> constructor = charToConstructorMap.get(c);
            if (constructor == null) return;
            constructor.setAccessible(true);
            MapObject object = constructor.newInstance(new GridPoint2(x, y));
            this.add(object);

            if (c == 'X') {
                this.player = object;
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<Character, Constructor<? extends MapObject>> getCharToConstructorMap() {
        Map<Character, Constructor<? extends MapObject>> charToConstructorMap = new HashMap<>();
        try {
            charToConstructorMap.put('T', Tree.class.getConstructor(GridPoint2.class));
            charToConstructorMap.put('X', Tank.class.getConstructor(GridPoint2.class));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return charToConstructorMap;
    }

    public void add(MapObject object) {
        handlerList.forEach(objectAddHandler -> objectAddHandler.add(object));
        objects.add(object);
    }

    public void applyInstructions() {
        Map.Entry<Instruction, MapObject> objectInstruction = inputController.getInstruction();
        if (objectInstruction != null) {
            objectInstruction.getKey().apply(objectInstruction.getValue());
        }
    }

    public void updateState(float deltaTime) {
        objects.forEach(mapObject -> mapObject.updateState(deltaTime));
    }

    public MapObject getPlayer() {
        return player;
    }
}
