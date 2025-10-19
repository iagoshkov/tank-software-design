package ru.mipt.bit.platformer.util;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import ru.mipt.bit.platformer.Direction;
import ru.mipt.bit.platformer.game.World;
public class InputHandler extends InputAdapter {
  private final World world;
  public InputHandler(World world) { this.world = world; }

  @Override
  public boolean keyDown(int keycode) {
    switch (keycode) {
    case Input.Keys.UP:
      world.getPlayerTank().move(Direction.UP);
      return true;
    case Input.Keys.DOWN:
      world.getPlayerTank().move(Direction.DOWN);
      return true;
    case Input.Keys.LEFT:
      world.getPlayerTank().move(Direction.LEFT);
      return true;
    case Input.Keys.RIGHT:
      world.getPlayerTank().move(Direction.RIGHT);
      return true;
    case Input.Keys.SPACE:
      world.shoot();
      return true;
    }
    return false;
  }
}