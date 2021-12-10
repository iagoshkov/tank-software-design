package ru.mipt.bit.platformer.driver;

import ru.mipt.bit.platformer.control.managers.ControlByAIAdaptor;
import ru.mipt.bit.platformer.control.managers.ControlByKey;
import ru.mipt.bit.platformer.control.managers.ControlByRandom;
import ru.mipt.bit.platformer.control.managers.Manager;
import ru.mipt.bit.platformer.control.commands.*;
import ru.mipt.bit.platformer.driver.observation.Event;
import ru.mipt.bit.platformer.driver.observation.Observer;
import ru.mipt.bit.platformer.objects.gameObjects.GameObject;

import java.util.ArrayList;
import java.util.List;

public class GameDriver implements Observer {

    private final LogicLevel logicLevel;
    private final List<Manager> managers;

    ControlByAIAdaptor aiController;

    public GameDriver(LogicLevel logicLevel) {
        this.logicLevel = logicLevel;
        managers = new ArrayList<>();
        aiController = new ControlByAIAdaptor(logicLevel);
    }

    public void createManagerForPlayer() {
        managers.add(new ControlByKey(new MoveUpCommand(logicLevel.getTanks().get(0)),
                                      new MoveDownCommand(logicLevel.getTanks().get(0)),
                                      new MoveLeftCommand(logicLevel.getTanks().get(0)),
                                      new MoveRightCommand(logicLevel.getTanks().get(0)),
                                      new ShootCommand(logicLevel.getTanks().get(0), logicLevel),
                                      this));
    }

    public void createManagerForTanks() {
        int i = managers.size();

        while (managers.size() < logicLevel.getTanks().size()) {

            managers.add(new ControlByRandom(new MoveUpCommand(logicLevel.getTanks().get(i)),
                                             new MoveDownCommand(logicLevel.getTanks().get(i)),
                                             new MoveLeftCommand(logicLevel.getTanks().get(i)),
                                             new MoveRightCommand(logicLevel.getTanks().get(i)),
                                             new ShootCommand(logicLevel.getTanks().get(i), logicLevel)));
            i++;
        }
    }

    public void generateCommands() {
        for (int i = 0; i < logicLevel.getTanks().size(); i++) {
            if (logicLevel.getTanks().get(i).hasFinishedMovement()) {
                managers.get(i).executeCommand();
            }
        }
    }

    public void updateObjects() {
        logicLevel.checkObjects();
        logicLevel.moveTanks();
        logicLevel.moveBullets();
    }

    @Override
    public void update(Event event, GameObject gameObject, int id) {
        if (event == Event.OnOffHealth) {
            logicLevel.levelRenderer.update(event, null, 0);
        }
    }
}
