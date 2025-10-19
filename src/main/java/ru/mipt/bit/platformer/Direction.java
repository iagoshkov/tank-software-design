package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
  UP(0, 1, 0f),
  DOWN(0, -1, 180f),
  LEFT(-1, 0, 90f),
  RIGHT(1, 0, 270f);

  private final GridPoint2 vector;
  private final float rotation;

  Direction(int x, int y, float rotation) {
    this.vector = new GridPoint2(x, y);
    this.rotation = rotation;
  }

  public GridPoint2 getVector() { return new GridPoint2(vector); }

  public float getRotation() { return rotation; }
}