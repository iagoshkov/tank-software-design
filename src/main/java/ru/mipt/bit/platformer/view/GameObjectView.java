package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.game.gameobject.GameObject;

public abstract class GameObjectView<T extends GameObject>
    implements Disposable {
  protected T gameObject;
  protected Texture texture;

  public GameObjectView(T gameObject) { this.gameObject = gameObject; }

  public abstract void draw(SpriteBatch batch, float worldUnitSize);

  public Texture getTexture() { return texture; }

  public void setTexture(Texture texture) { this.texture = texture; }

  @Override
  public void dispose() {
    if (texture != null) {
      texture.dispose();
    }
  }
}