package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;

public class Player {
    /*
    Класс игрока. Мог быть отнаследован от LevelObject, но вы сказали так не делать. Хотя с наследованием бы вышло
    меньше кода.
     */
    private final LevelObject playerObject;

    public Player(LevelObject playerObject) {
        this.playerObject = playerObject;
    }

    public void setCoordinates(GridPoint2 coordinates) {
        this.playerObject.setCoordinates(coordinates.x, coordinates.y);
    }

    public LevelObject getPlayerObject() {
        return playerObject;
    }

    public GridPoint2 getCoordinates() {
        return this.playerObject.getCoordinates();
    }

    public void setRotation(float rotation) {
        this.playerObject.setRotation(rotation);
    }

}
