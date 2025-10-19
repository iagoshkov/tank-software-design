package ru.mipt.bit.platformer.game.gameobject;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import ru.mipt.bit.platformer.Direction;

public class Bullet extends GameObject {
  private final Direction direction;
  private final Vector2 preciseCoordinates;
  private static final float SPEED = 5f; // tiles per second

  public Bullet(GridPoint2 startCoordinates, Direction direction) {
    super(startCoordinates);
    this.direction = direction;
    this.preciseCoordinates =
        new Vector2(startCoordinates.x, startCoordinates.y);
  }

  public void update(float delta) {
    preciseCoordinates.x += direction.getVector().x * SPEED * delta;
    preciseCoordinates.y += direction.getVector().y * SPEED * delta;
    coordinates.set((int)Math.floor(preciseCoordinates.x),
                    (int)Math.floor(preciseCoordinates.y));
  }

  public Direction getDirection() { return direction; }

  public Vector2 getPreciseCoordinates() { return preciseCoordinates; }
}
