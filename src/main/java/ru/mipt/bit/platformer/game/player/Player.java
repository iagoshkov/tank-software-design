package ru.mipt.bit.platformer.game.player;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.level.LevelEntity;

public class Player {
    /*
    Класс игрока. Мог быть отнаследован от LevelObject, но вы сказали так не делать. Хотя с наследованием бы вышло
    меньше кода.
     */
    private final LevelEntity playerObject;

    public Player(LevelEntity playerObject) {
        this.playerObject = playerObject;
    }

    public void setCoordinates(GridPoint2 coordinates) {
        this.playerObject.setCoordinates(coordinates.x, coordinates.y);
    }

    public LevelEntity getPlayerObject() {
        return playerObject;
    }

    public GridPoint2 getCoordinates() {
        return this.playerObject.getCoordinates();
    }

    public void setRotation(float rotation) {
        this.playerObject.setRotation(rotation);
    }

}
