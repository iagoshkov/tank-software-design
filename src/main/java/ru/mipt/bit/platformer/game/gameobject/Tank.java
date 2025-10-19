package ru.mipt.bit.platformer.game.gameobject;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Direction;

public class Tank extends GameObject {
  private Direction direction = Direction.UP;

  public Tank(GridPoint2 initialPosition) { super(initialPosition); }

  public void move(Direction newDirection) {
    this.direction = newDirection;
    this.coordinates.add(newDirection.getVector());
  }

  public Direction getDirection() { return direction; }
}
