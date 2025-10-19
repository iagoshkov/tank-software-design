package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import ru.mipt.bit.platformer.util.InputHandler;
import ru.mipt.bit.platformer.view.WorldRenderer;

public class GameScreen extends ScreenAdapter {
  private World world;
  private WorldRenderer worldRenderer;

  @Override
  public void show() {
    world = new World();
    worldRenderer = new WorldRenderer(world);
    Gdx.input.setInputProcessor(new InputHandler(world));
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0.2f, 0.5f, 0.2f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    world.update(delta);
    worldRenderer.render();
  }

  @Override
  public void resize(int width, int height) {
    worldRenderer.resize(width, height);
  }

  @Override
  public void dispose() {
    worldRenderer.dispose();
  }
}