package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.mipt.bit.platformer.game.World;
import ru.mipt.bit.platformer.game.gameobject.Bullet;
import ru.mipt.bit.platformer.game.gameobject.Tree;

public class WorldRenderer implements Disposable {
  private final World world;
  private final SpriteBatch batch;
  private final OrthographicCamera camera;
  private final Viewport viewport;

  private final TankView tankView;
  private final Array<TreeView> treeViews = new Array<>();
  private final Array<BulletView> bulletViews = new Array<>();
  private final BulletView bulletViewPrototype;

  private static final float TILE_SIZE = 48f;
  private static final float VIEWPORT_WIDTH_IN_TILES = 15f;

  public WorldRenderer(World world) {
    this.world = world;
    this.batch = new SpriteBatch();
    this.camera = new OrthographicCamera();

    float viewportHeight =
        VIEWPORT_WIDTH_IN_TILES *
        ((float)Gdx.graphics.getHeight() / Gdx.graphics.getWidth());
    this.viewport =
        new FitViewport(VIEWPORT_WIDTH_IN_TILES, viewportHeight, camera);
    this.viewport.apply();

    // Center the camera
    camera.position.set(viewport.getWorldWidth() / 2,
                        viewport.getWorldHeight() / 2, 0);
    camera.update();

    tankView = new TankView(world.getPlayerTank());
    for (Tree tree : world.getTrees()) {
      treeViews.add(new TreeView(tree));
    }
    bulletViewPrototype = new BulletView(null);
  }

  public void render() {
    batch.setProjectionMatrix(camera.combined);

    updateBulletViews();

    batch.begin();
    tankView.draw(batch,
                  1f); // Use 1.0f for tile size since viewport handles scaling
    for (TreeView view : treeViews) {
      view.draw(batch, 1f);
    }
    for (BulletView view : bulletViews) {
      view.draw(batch, 1f);
    }
    batch.end();
  }

  private void updateBulletViews() {
    bulletViews.clear();
    for (Bullet bullet : world.getBullets()) {
      BulletView view = new BulletView(bullet);
      view.setTexture(bulletViewPrototype.getTexture());
      bulletViews.add(view);
    }
  }

  public void resize(int width, int height) {
    viewport.update(width, height, true); // Center camera on resize
  }

  @Override
  public void dispose() {
    batch.dispose();
    tankView.dispose();
    bulletViewPrototype.dispose();
    for (TreeView view : treeViews) {
      view.dispose();
    }
  }
}
