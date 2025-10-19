package ru.mipt.bit.platformer;

import com.badlogic.gdx.Game;
import ru.mipt.bit.platformer.game.GameScreen;

public class PlatformerGame extends Game {
  @Override
  public void create() {
    setScreen(new GameScreen());
  }
}