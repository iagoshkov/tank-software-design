package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.Entities.MapObject;
import ru.mipt.bit.platformer.Entities.Tank;
import ru.mipt.bit.platformer.Instructions.Direction;
import ru.mipt.bit.platformer.Instructions.Instruction;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private final GraphicsController graphicsController;
    private final InputController inputController;

    private final Tank player;
    private final List<MapObject> objects = new ArrayList<>();

    public Level(Tank player, GraphicsController graphicsController, InputController inputController) {
        this.player = player;
        this.graphicsController = graphicsController;
        this.inputController = inputController;

        add(player);
    }

    public void add(MapObject object) {
        graphicsController.addGraphicsOf(object);
        objects.add(object);
    }

    public void moveObjects(float deltaTime) {
        Instruction instruction = inputController.getInstruction();

        if (instruction instanceof Direction) {
            player.moveIfNotCollides((Direction) instruction, objects);
        }

        graphicsController.moveRectangles();

        player.updateState(deltaTime);
    }

}
