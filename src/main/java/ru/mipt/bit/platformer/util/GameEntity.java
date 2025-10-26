package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.g2d.Batch;

public class GameEntity {
    private final GameObjectModel model;
    private final GameObjectView view;

    public GameEntity(GameObjectModel model, GameObjectView view) {
        this.model = model;
        this.view = view;
    }

    public void update(float deltaTime) {
        model.update(deltaTime);
        view.update(deltaTime);
    }

    public void render(Batch batch) {
        view.render(batch);
    }

    public GameObjectModel getModel() {
        return model;
    }

    public GameObjectView getView() {
        return view;
    }
}