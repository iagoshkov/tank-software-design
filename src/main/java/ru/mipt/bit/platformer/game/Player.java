package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;

public class Player {
    private final LevelObject playerObject;
    private GridPoint2 destCoordinates;

    public Player(LevelObject playerObject) {
        this.playerObject = playerObject;
        this.destCoordinates = playerObject.getCoordinates();
    }

    public void setCoordinates(GridPoint2 coordinates) {
        this.playerObject.setCoordinates(coordinates.x, coordinates.y);
    }

    public void setDestination(GridPoint2 coordinates) {
        this.destCoordinates = coordinates;
    }

    public LevelObject getPlayerObject() {
        return playerObject;
    }

    public GridPoint2 getDestination() {
        return this.destCoordinates;
    }

    public void setRotation(float rotation) {
        this.playerObject.setRotation(rotation);
    }

}
