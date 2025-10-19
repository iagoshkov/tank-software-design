package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;
import java.util.ArrayList;
import java.util.List;
import ru.mipt.bit.platformer.game.gameobject.Bullet;
import ru.mipt.bit.platformer.game.gameobject.Tank;
import ru.mipt.bit.platformer.game.gameobject.Tree;

public class World {
  private final Tank playerTank;
  private final ArrayList<Tree> trees = new ArrayList<>();
  private final ArrayList<Bullet> bullets = new ArrayList<>();
  private final ArrayList<Bullet> bulletsToRemove = new ArrayList<>();

  public World() {
    playerTank = new Tank(new GridPoint2(1, 1));

    trees.add(new Tree(new GridPoint2(5, 5)));
    trees.add(new Tree(new GridPoint2(5, 6)));
    trees.add(new Tree(new GridPoint2(6, 5)));
  }

  public void update(float delta) {
    for (Bullet bullet : bullets) {
      bullet.update(delta);
      if (bullet.getCoordinates().x < -1 || bullet.getCoordinates().x > 20 ||
          bullet.getCoordinates().y < -1 || bullet.getCoordinates().y > 20) {
        bulletsToRemove.add(bullet);
      }
    }
    bullets.removeAll(bulletsToRemove);
    bulletsToRemove.clear();
  }

  public void shoot() {
    bullets.add(
        new Bullet(playerTank.getCoordinates(), playerTank.getDirection()));
  }

  public Tank getPlayerTank() { return playerTank; }

  public List<Tree> getTrees() { return trees; }

  public List<Bullet> getBullets() { return bullets; }
}