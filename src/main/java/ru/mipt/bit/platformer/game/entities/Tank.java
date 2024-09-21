package ru.mipt.bit.platformer.game.entities;

import ru.mipt.bit.platformer.game.player.Player;

public class Tank implements Player {
    /*
    Класс игрока.
     */
    private Coordinates coordinates;
    private float rotation = 0f;
    public Tank(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = new Coordinates(coordinates.x, coordinates.y);
    }

    @Override
    public float getRotation() {
        return rotation;
    }

    @Override
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
