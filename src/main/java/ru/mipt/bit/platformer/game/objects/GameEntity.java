package ru.mipt.bit.platformer.game.objects;

public class GameEntity {
    /*
    Класс сущности игры без привязки к графике.
     */
    private Coordinates coordinates;
    private float rotation = 0f;

    public GameEntity(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public float getRotation() {
        return rotation;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = new Coordinates(coordinates.x, coordinates.y);
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
