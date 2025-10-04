package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.Direction;
import ru.mipt.bit.platformer.model.PlayerModel;
import ru.mipt.bit.platformer.view.PlayerView;

public class PlayerController extends EntityController<PlayerModel, PlayerView> {
    public PlayerController(PlayerModel entityModel, PlayerView entityView) {
        super(entityModel, entityView);
    }

    @Override
    public void move(Direction dir, LevelController levelController) {
        entityModel.move(dir, levelController);
    }

    @Override
    public void update(float delta) {
        entityModel.update(delta);
    }
}
