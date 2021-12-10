package ru.mipt.bit.platformer.control.managers;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.control.Control;
import ru.mipt.bit.platformer.control.commands.Command;
import ru.mipt.bit.platformer.driver.GameDriver;
import ru.mipt.bit.platformer.driver.observation.Event;
import ru.mipt.bit.platformer.driver.observation.Observable;
import ru.mipt.bit.platformer.driver.observation.Observer;
import ru.mipt.bit.platformer.objects.gameObjects.GameObject;

import java.util.Date;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;

public class ControlByKey implements Manager, Observable {
    private final Control control;
    private final GameDriver  gameDriver;
    private long lastTimeChanged = new Date().getTime();

    public ControlByKey(Command moveUpCommand,
                        Command moveDownCommand,
                        Command moveLeftCommand,
                        Command moveRightCommand,
                        Command shootCommand,
                        GameDriver gameDriver) {
        control = new Control(moveUpCommand, moveDownCommand, moveLeftCommand, moveRightCommand, shootCommand);
        this.gameDriver = gameDriver;
    }

    @Override
    public void executeCommand() {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            control.moveUp();
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            control.moveLeft();
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            control.moveDown();
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            control.moveRight();
        }
        if (Gdx.input.isKeyPressed(SPACE)){
            control.shoot();
        }
        if (Gdx.input.isKeyPressed(L)) {
            long time = new Date().getTime();
            if (time - lastTimeChanged > 300) {
                notifyObservers(Event.OnOffHealth, null);
                lastTimeChanged = time;
            }
        }
    }

    @Override
    public void addObserver(Observer o) {

    }

    @Override
    public void removeObserver(Observer o) {

    }

    @Override
    public void notifyObservers(Event event, GameObject object) {
        if (event == Event.OnOffHealth) {
            gameDriver.update(event, null, 0);
        }
    }
}
