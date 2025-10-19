package ru.mipt.bit.platformer.game.gameobject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.Direction;

public class TankTest {

  private Tank tank;

  @BeforeEach
  public void setup() {
    tank = new Tank(new GridPoint2(5, 5));
  }

  @Test
  public void testInitialPositionAndDirection() {
    assertEquals(new GridPoint2(5, 5), tank.getCoordinates());
    assertEquals(Direction.UP, tank.getDirection());
  }

  @Test
  public void testMoveUp() {
    tank.move(Direction.UP);
    assertEquals(new GridPoint2(5, 6), tank.getCoordinates());
    assertEquals(Direction.UP, tank.getDirection());
  }
}