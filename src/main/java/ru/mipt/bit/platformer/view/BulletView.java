package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import ru.mipt.bit.platformer.game.gameobject.Bullet;

public class BulletView extends GameObjectView<Bullet> {
  public BulletView(Bullet bullet) {
    super(bullet);
    if (bullet == null) { // Only load texture for the prototype
      texture = new Texture("images/bullet.png");
    }
  }

  @Override
  public void draw(SpriteBatch batch, float worldUnitSize) {
    if (gameObject == null)
      return;
    Vector2 pos = gameObject.getPreciseCoordinates();
    float rotation = gameObject.getDirection().getRotation();

    float bulletSize = worldUnitSize / 3f;
    float offset = (worldUnitSize - bulletSize) / 2f;

    batch.draw(texture, pos.x + offset, pos.y + offset, bulletSize / 2f,
               bulletSize / 2f, bulletSize, bulletSize, 1f, 1f, rotation, 0, 0,
               texture.getWidth(), texture.getHeight(), false, false);
  }
}