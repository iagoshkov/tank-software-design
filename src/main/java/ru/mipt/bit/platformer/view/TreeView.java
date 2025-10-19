package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.gameobject.Tree;

public class TreeView extends GameObjectView<Tree> {
  public TreeView(Tree tree) {
    super(tree);
    texture = new Texture("images/greenTree.png");
  }

  @Override
  public void draw(SpriteBatch batch, float worldUnitSize) {
    GridPoint2 pos = gameObject.getCoordinates();
    batch.draw(texture, pos.x, pos.y, worldUnitSize, worldUnitSize);
  }
}