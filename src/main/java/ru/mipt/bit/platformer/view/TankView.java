package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.gameobject.Tank;

public class TankView extends GameObjectView<Tank> {
  public TankView(Tank tank) {
    super(tank);
    texture = new Texture("images/tank_blue.png");
  }

  @Override
  public void draw(SpriteBatch batch, float worldUnitSize) {
    GridPoint2 pos = gameObject.getCoordinates();
    float rotation = gameObject.getDirection().getRotation();

    batch.draw(texture, pos.x, pos.y, worldUnitSize / 2f, worldUnitSize / 2f,
               worldUnitSize, worldUnitSize, 1f, 1f, rotation, 0, 0,
               texture.getWidth(), texture.getHeight(), false, false);
  }
}