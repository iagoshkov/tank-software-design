package ru.mipt.bit.platformer.game.player;

import ru.mipt.bit.platformer.game.level.LevelEntity;
import ru.mipt.bit.platformer.game.level.Point;

public class Player {
    /*
    Класс игрока. Мог быть отнаследован от LevelObject, но вы сказали так не делать. Хотя с наследованием бы вышло
    меньше кода.
     */
    private final LevelEntity playerEntity;

    public Player(LevelEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public void setCoordinates(Point coordinate) {
        this.playerEntity.setCoordinates(coordinate);
    }

    public LevelEntity getPlayerEntity() {
        return playerEntity;
    }

    public Point getCoordinates() {
        return playerEntity.getCoordinates();
    }

    public void setRotation(float rotation) {
        this.playerEntity.setRotation(rotation);
    }

}
