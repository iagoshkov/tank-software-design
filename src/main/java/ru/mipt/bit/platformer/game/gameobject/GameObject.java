package ru.mipt.bit.platformer.game.gameobject;

import com.badlogic.gdx.math.GridPoint2;

public abstract class GameObject {
  protected final GridPoint2 coordinates;

  public GameObject(GridPoint2 initialCoordinates) {
    this.coordinates = new GridPoint2(initialCoordinates);
  }

  public GridPoint2 getCoordinates() { return new GridPoint2(coordinates); }
}